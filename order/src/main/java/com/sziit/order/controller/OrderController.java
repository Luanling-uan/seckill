package com.sziit.order.controller;

import com.sziit.order.entity.Order;
import com.sziit.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id);
    }

    @GetMapping("/timeout")
    public List<com.sziit.common.dto.OrderDTO> getTimeoutOrders() {
        long now = System.currentTimeMillis();
        List<com.sziit.common.dto.OrderDTO> result = new java.util.ArrayList<>();
        for (Order order : orderRepository.findAll().values()) {
            if ("CREATED".equals(order.getStatus()) && now - order.getCreateTime() > 5 * 60 * 1000) {
                com.sziit.common.dto.OrderDTO dto = new com.sziit.common.dto.OrderDTO();
                dto.setId(order.getId());
                dto.setUserId(order.getUserId());
                dto.setProductId(order.getProductId());
                dto.setCreateTime(order.getCreateTime());
                dto.setStatus(order.getStatus());
                result.add(dto);
            }
        }
        return result;
    }
} 