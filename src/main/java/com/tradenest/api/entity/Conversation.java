package com.tradenest.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversations",
       uniqueConstraints = @UniqueConstraint(
               columnNames = {"buyer_id", "seller_id", "product_id"}
       ))
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String lastMessage;
    private LocalDateTime lastMessageAt;

    private int buyerUnread;
    private int sellerUnread;

    public Conversation() {}

    @PrePersist
    public void prePersist() {
        lastMessageAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getBuyer() { return buyer; }
    public User getSeller() { return seller; }
    public Product getProduct() { return product; }
    public String getLastMessage() { return lastMessage; }
    public LocalDateTime getLastMessageAt() { return lastMessageAt; }
    public int getBuyerUnread() { return buyerUnread; }
    public int getSellerUnread() { return sellerUnread; }

    public void setBuyer(User buyer) { this.buyer = buyer; }
    public void setSeller(User seller) { this.seller = seller; }
    public void setProduct(Product product) { this.product = product; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public void setLastMessageAt(LocalDateTime lastMessageAt) { this.lastMessageAt = lastMessageAt; }
    public void setBuyerUnread(int buyerUnread) { this.buyerUnread = buyerUnread; }
    public void setSellerUnread(int sellerUnread) { this.sellerUnread = sellerUnread; }
}