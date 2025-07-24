package com.sziit.order.service.impl;

import com.sziit.common.dto.OrderMessageDTO;
import com.sziit.order.entity.Order;
import com.sziit.order.repository.OrderRepository;
import com.sziit.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public void createOrder(OrderMessageDTO msg) {
        Order order = new Order();
        order.setUserId(msg.getUserId());
        order.setProductId(msg.getProductId());
        order.setCreateTime(msg.getTimestamp());
        order.setStatus("CREATED");
        orderRepository.save(order);
    }
} 