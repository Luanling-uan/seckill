package com.sziit.common.feign;

import com.sziit.common.dto.SeckillRequestDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

    @PutMapping("/api/products/{productId}/release-stock")
    @CircuitBreaker(name = "productService", fallbackMethod = "releaseStockFallback")
    void releaseStock(@PathVariable("productId") Long productId,
                      @RequestParam("quantity") Integer quantity);
}