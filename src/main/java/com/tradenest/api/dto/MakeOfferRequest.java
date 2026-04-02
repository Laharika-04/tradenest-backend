package com.tradenest.api.dto;

import java.math.BigDecimal;

public class MakeOfferRequest {

    private Long productId;
    private BigDecimal amount;
    private String message;

    public Long getProductId() { return productId; }
    public BigDecimal getAmount() { return amount; }
    public String getMessage() { return message; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setMessage(String message) { this.message = message; }
}