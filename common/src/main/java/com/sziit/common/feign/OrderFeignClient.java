package com.sziit.common.feign;

import com.sziit.common.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderFeignClient {

    /**
     * 查询超时未支付的订单
     * @param timeoutMinutes 超时时间（分钟）
     * @return 超时订单列表
     */
    @GetMapping("/api/orders/timeout")
    List<OrderDTO> getTimeoutOrders(@RequestParam("timeoutMinutes") int timeoutMinutes);

    /**
     * 取消订单
     * @param orderId 订单ID
     * @return 取消结果
     */
    @PutMapping("/api/orders/{orderId}/cancel")
    boolean cancelOrder(@PathVariable("orderId") Long orderId);
}

