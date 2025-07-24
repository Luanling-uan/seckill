package com.sziit.order.repository;

import com.sziit.order.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {
    private static final Map<Long, Order> DB = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GEN = new AtomicLong(1);
    public void save(Order order) {
        order.setId(ID_GEN.getAndIncrement());
        DB.put(order.getId(), order);
    }
    public Order findById(Long id) {
        return DB.get(id);
    }
    public Map<Long, Order> findAll() {
        return DB;
    }
} 