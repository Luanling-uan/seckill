package com.sziit.common.feign;

import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {

    @Override
    public void releaseStock(Long productId, Integer quantity) {
        // 降级逻辑：记录日志、发送告警或执行默认操作
        System.out.println("库存释放失败，进入降级处理: productId=" + productId + ", quantity=" + quantity);
    }
}