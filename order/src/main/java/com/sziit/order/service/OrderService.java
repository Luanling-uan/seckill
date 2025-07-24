package com.sziit.order.service;

import com.sziit.common.dto.OrderMessageDTO;
 
public interface OrderService {
    void createOrder(OrderMessageDTO msg);
} 