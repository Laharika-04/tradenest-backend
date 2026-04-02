package com.tradenest.api.dto;

import com.tradenest.api.entity.Wishlist;

public class WishlistDto {

    private Long productId;
    private String title;
    private String city;
    private String image;

    public static WishlistDto fromEntity(Wishlist w) {
        WishlistDto d = new WishlistDto();
        d.productId = w.getProduct().getId();
        d.title = w.getProduct().getTitle();
        d.city = w.getProduct().getCity();
        d.image = w.getProduct().getImages();
        return d;
    }

    public Long getProductId() { return productId; }
    public String getTitle() { return title; }
    public String getCity() { return city; }
    public String getImage() { return image; }
}