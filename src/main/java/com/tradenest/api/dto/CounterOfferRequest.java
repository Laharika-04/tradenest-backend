package com.tradenest.api.dto;

import java.math.BigDecimal;

public class CounterOfferRequest {

    private BigDecimal amount;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}