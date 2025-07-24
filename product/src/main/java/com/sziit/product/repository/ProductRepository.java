package com.sziit.product.repository;

import com.sziit.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private static final Map<Long, Product> DB = new ConcurrentHashMap<>();
    static {
        Product p = new Product();
        p.setId(1L);
        p.setName("秒杀商品A");
        p.setStock(100);
        DB.put(1L, p);
    }
    public Product findById(Long id) {
        return DB.get(id);
    }
    public void decreaseStock(Long id, int amount) {
        Product p = DB.get(id);
        if (p != null && p.getStock() >= amount) {
            p.setStock(p.getStock() - amount);
        }
    }
} 