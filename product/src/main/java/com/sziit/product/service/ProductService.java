package com.sziit.product.service;

import com.sziit.common.dto.SeckillRequestDTO;
import com.sziit.common.dto.SeckillResultDTO;

public interface ProductService {
    SeckillResultDTO seckill(SeckillRequestDTO request);
    boolean decreaseStock(SeckillRequestDTO request);
} 