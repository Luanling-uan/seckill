package com.sziit.job.task;

import com.sziit.common.dto.OrderDTO;
import com.sziit.common.feign.OrderFeignClient;
import com.sziit.common.feign.ProductFeignClient;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTimeoutJob {
    private static final Logger logger = LoggerFactory.getLogger(OrderTimeoutJob.class);
    private static final int ORDER_TIMEOUT_MINUTES = 15; // 订单超时时间（分钟）

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @XxlJob("orderTimeoutJob")
    public void execute() {
        try {
            // 1. 记录任务开始时间
            LocalDateTime startTime = LocalDateTime.now();
            logger.info("开始执行订单超时清理任务，时间: {}", startTime);
            XxlJobHelper.log("开始执行订单超时清理任务，时间: {}", startTime);

            // 2. 查询超时未支付的订单
            List<OrderDTO> timeoutOrders = orderFeignClient.getTimeoutOrders(ORDER_TIMEOUT_MINUTES);
            logger.info("查询到 {} 个超时订单", timeoutOrders.size());
            XxlJobHelper.log("查询到 {} 个超时订单", timeoutOrders.size());

            // 3. 遍历处理每个超时订单
            int successCount = 0;
            int failedCount = 0;
            for (OrderDTO order : timeoutOrders) {
                try {
                    // 3.1 取消订单
                    boolean cancelResult = orderFeignClient.cancelOrder(order.getId());
                    if (cancelResult) {
                        // 3.2 释放库存
                        productFeignClient.releaseStock(order.getProductId(), order.getQuantity());
                        successCount++;
                        logger.info("订单 {} 已成功取消并释放库存", order.getId());
                        XxlJobHelper.log("订单 {} 已成功取消并释放库存", order.getId());
                    } else {
                        failedCount++;
                        logger.error("订单 {} 取消失败", order.getId());
                        XxlJobHelper.log("订单 {} 取消失败", order.getId());
                    }
                } catch (Exception e) {
                    failedCount++;
                    logger.error("处理订单 {} 时发生异常", order.getId(), e);
                    XxlJobHelper.log("处理订单 {} 时发生异常: {}", order.getId(), e.getMessage());
                }
            }

            // 4. 记录任务结束信息
            LocalDateTime endTime = LocalDateTime.now();
            logger.info("订单超时清理任务执行完成，成功: {}, 失败: {}, 耗时: {}秒",
                    successCount, failedCount, java.time.Duration.between(startTime, endTime).getSeconds());
            XxlJobHelper.log("订单超时清理任务执行完成，成功: {}, 失败: {}, 耗时: {}秒",
                    successCount, failedCount, java.time.Duration.between(startTime, endTime).getSeconds());

            // 5. 返回任务执行结果
            XxlJobHelper.handleSuccess("处理完成: 成功=" + successCount + ", 失败=" + failedCount);
        } catch (Exception e) {
            logger.error("执行订单超时清理任务时发生系统异常", e);
            XxlJobHelper.handleFail("执行任务异常: " + e.getMessage());
            throw e;
        }
    }
}