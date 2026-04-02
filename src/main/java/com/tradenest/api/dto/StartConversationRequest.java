package com.tradenest.api.dto;

public class StartConversationRequest {

    private Long productId;
    private Long sellerId;
    private String message;

    public Long getProductId() { return productId; }
    public Long getSellerId() { return sellerId; }
    public String getMessage() { return message; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public void setMessage(String message) { this.message = message; }
}