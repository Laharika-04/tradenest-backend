package com.tradenest.api.dto;

import com.tradenest.api.entity.Conversation;

public class ConversationDto {

    private Long   id;
    private Long   productId;
    private String productTitle;   // NEW
    private Long   buyerId;
    private String buyerName;      // NEW
    private Long   sellerId;
    private String sellerName;     // NEW
    private String lastMessage;

    public static ConversationDto fromEntity(Conversation c) {
        ConversationDto d = new ConversationDto();
        d.id           = c.getId();
        d.productId    = c.getProduct().getId();
        d.productTitle = c.getProduct().getTitle();   // NEW
        d.buyerId      = c.getBuyer().getId();
        d.buyerName    = c.getBuyer().getName();      // NEW
        d.sellerId     = c.getSeller().getId();
        d.sellerName   = c.getSeller().getName();     // NEW
        d.lastMessage  = c.getLastMessage();
        return d;
    }

    public Long   getId()           { return id; }
    public Long   getProductId()    { return productId; }
    public String getProductTitle() { return productTitle; }
    public Long   getBuyerId()      { return buyerId; }
    public String getBuyerName()    { return buyerName; }
    public Long   getSellerId()     { return sellerId; }
    public String getSellerName()   { return sellerName; }
    public String getLastMessage()  { return lastMessage; }
}