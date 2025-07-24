package com.sziit.common.dto;

import java.io.Serializable;

public class OrderMessageDTO implements Serializable {
    private Long userId;
    private Long productId;
    private Long timestamp;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
} 