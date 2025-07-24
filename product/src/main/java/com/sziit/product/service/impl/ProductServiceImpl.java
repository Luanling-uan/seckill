package com.sziit.product.service.impl;

import com.sziit.common.dto.SeckillRequestDTO;
import com.sziit.common.dto.SeckillResultDTO;
import com.sziit.common.dto.OrderMessageDTO;
import com.sziit.product.entity.Product;
import com.sziit.product.repository.ProductRepository;
import com.sziit.product.service.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final String LOCK_PREFIX = "seckill:lock:";
    @Override
    public SeckillResultDTO seckill(SeckillRequestDTO request) {
        String lockKey = LOCK_PREFIX + request.getProductId();
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 5, TimeUnit.SECONDS);
        if (locked == null || !locked) {
            return new SeckillResultDTO(false, "系统繁忙，请稍后重试");
        }
        try {
            Product product = productRepository.findById(request.getProductId());
            if (product == null || product.getStock() <= 0) {
                return new SeckillResultDTO(false, "商品已售罄");
            }
            productRepository.decreaseStock(request.getProductId(), 1);
            OrderMessageDTO msg = new OrderMessageDTO();
            msg.setUserId(request.getUserId());
            msg.setProductId(request.getProductId());
            msg.setTimestamp(System.currentTimeMillis());
            rabbitTemplate.convertAndSend("order.exchange", "order.seckill", msg);
            return new SeckillResultDTO(true, "秒杀成功，正在为您创建订单");
        } finally {
            redisTemplate.delete(lockKey);
        }
    }
    @Override
    public boolean decreaseStock(SeckillRequestDTO request) {
        Product product = productRepository.findById(request.getProductId());
        if (product != null && product.getStock() > 0) {
            productRepository.decreaseStock(request.getProductId(), 1);
            return true;
        }
        return false;
    }
} 