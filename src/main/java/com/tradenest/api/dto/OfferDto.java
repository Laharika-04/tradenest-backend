package com.tradenest.api.dto;

import com.tradenest.api.entity.Offer;
import java.math.BigDecimal;

public class OfferDto {

    private Long       id;
    private Long       productId;
    private String     productTitle;   // FIX: show title in OffersPage instead of "Product #N"
    private Long       buyerId;
    private String     buyerName;      // FIX: useful for seller-side received offers
    private BigDecimal amount;
    private String     status;
    private BigDecimal counterAmount;
    private String     message;        // FIX: was missing — frontend displays this

    public static OfferDto fromEntity(Offer o) {
        OfferDto d = new OfferDto();
        d.id            = o.getId();
        d.productId     = o.getProduct().getId();
        d.productTitle  = o.getProduct().getTitle();           // FIX
        d.buyerId       = o.getBuyer().getId();
        d.buyerName     = o.getBuyer().getName();              // FIX
        d.amount        = o.getAmount();
        d.status        = o.getStatus().name();
        d.counterAmount = o.getCounterAmount();
        d.message       = o.getMessage();                      // FIX
        return d;
    }

    public Long       getId()            { return id; }
    public Long       getProductId()     { return productId; }
    public String     getProductTitle()  { return productTitle; }
    public Long       getBuyerId()       { return buyerId; }
    public String     getBuyerName()     { return buyerName; }
    public BigDecimal getAmount()        { return amount; }
    public String     getStatus()        { return status; }
    public BigDecimal getCounterAmount() { return counterAmount; }
    public String     getMessage()       { return message; }
}