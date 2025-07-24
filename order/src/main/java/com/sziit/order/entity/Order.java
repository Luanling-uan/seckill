package com.sziit.order.entity;

public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private Long createTime;
    private String status; // CREATED, CANCELLED, PAID

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getCreateTime() { return createTime; }
    public void setCreateTime(Long createTime) { this.createTime = createTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 