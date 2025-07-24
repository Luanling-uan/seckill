package com.sziit.order.mq;

import com.sziit.common.dto.OrderMessageDTO;
import com.sziit.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {
    @Autowired
    private OrderService orderService;
    @RabbitListener(queues = "order.seckill.queue")
    public void onMessage(OrderMessageDTO msg) {
        orderService.createOrder(msg);
    }
} 