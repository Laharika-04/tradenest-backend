package com.tradenest.api.entity;

import com.tradenest.api.enums.OfferStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private BigDecimal amount;
    private String message;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    private BigDecimal counterAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Offer() {}

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = OfferStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getBuyer() { return buyer; }
    public Product getProduct() { return product; }
    public BigDecimal getAmount() { return amount; }
    public String getMessage() { return message; }
    public OfferStatus getStatus() { return status; }
    public BigDecimal getCounterAmount() { return counterAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setBuyer(User buyer) { this.buyer = buyer; }
    public void setProduct(Product product) { this.product = product; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setMessage(String message) { this.message = message; }
    public void setStatus(OfferStatus status) { this.status = status; }
    public void setCounterAmount(BigDecimal counterAmount) { this.counterAmount = counterAmount; }
}