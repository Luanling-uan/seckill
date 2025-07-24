package com.sziit.product.controller;

import com.sziit.common.dto.SeckillRequestDTO;
import com.sziit.common.dto.SeckillResultDTO;
import com.sziit.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/seckill")
    public SeckillResultDTO seckill(@RequestBody SeckillRequestDTO request) {
        return productService.seckill(request);
    }
    @PostMapping("/stock/decrease")
    public boolean decreaseStock(@RequestBody SeckillRequestDTO request) {
        return productService.decreaseStock(request);
    }
} 