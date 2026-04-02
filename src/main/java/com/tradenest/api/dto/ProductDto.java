package com.tradenest.api.dto;

import com.tradenest.api.entity.Product;
import com.tradenest.api.enums.ProductCondition;
import com.tradenest.api.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

    private Long             id;
    private String           title;
    private String           description;
    private BigDecimal       price;
    private String           city;
    private String           state;
    private String           images;
    private boolean          featured;
    private int              viewCount;
    private LocalDateTime    postedDate;
    private ProductStatus    status;
    private ProductCondition condition;   // NEW

    // Flat category
    private Long   categoryId;
    private String categoryName;
    private String categorySlug;
    private String categoryIcon;

    // Flat seller
    private Long    sellerId;
    private String  sellerName;
    private String  sellerCity;
    private String  sellerAvatarUrl;
    private boolean sellerVerified;

    public ProductDto() {}

    public static ProductDto fromEntity(Product p) {
        ProductDto d = new ProductDto();
        d.id          = p.getId();
        d.title       = p.getTitle();
        d.description = p.getDescription();
        d.price       = p.getPrice();
        d.city        = p.getCity();
        d.state       = p.getState();
        d.images      = p.getImages();
        d.featured    = p.isFeatured();
        d.viewCount   = p.getViewCount();
        d.postedDate  = p.getPostedDate();
        d.status      = p.getStatus();
        d.condition   = p.getCondition();   // NEW

        if (p.getCategory() != null) {
            d.categoryId   = p.getCategory().getId();
            d.categoryName = p.getCategory().getName();
            d.categorySlug = p.getCategory().getSlug();
            d.categoryIcon = p.getCategory().getIcon();
        }

        if (p.getSeller() != null) {
            d.sellerId        = p.getSeller().getId();
            d.sellerName      = p.getSeller().getName();
            d.sellerCity      = p.getSeller().getCity();
            d.sellerAvatarUrl = p.getSeller().getAvatarUrl();
            d.sellerVerified  = p.getSeller().isVerified();
        }

        return d;
    }

    // Getters
    public Long             getId()             { return id; }
    public String           getTitle()          { return title; }
    public String           getDescription()    { return description; }
    public BigDecimal       getPrice()          { return price; }
    public String           getCity()           { return city; }
    public String           getState()          { return state; }
    public String           getImages()         { return images; }
    public boolean          isFeatured()        { return featured; }
    public int              getViewCount()      { return viewCount; }
    public LocalDateTime    getPostedDate()     { return postedDate; }
    public ProductStatus    getStatus()         { return status; }
    public ProductCondition getCondition()      { return condition; }

    public Long    getCategoryId()              { return categoryId; }
    public String  getCategoryName()            { return categoryName; }
    public String  getCategorySlug()            { return categorySlug; }
    public String  getCategoryIcon()            { return categoryIcon; }

    public Long    getSellerId()                { return sellerId; }
    public String  getSellerName()              { return sellerName; }
    public String  getSellerCity()              { return sellerCity; }
    public String  getSellerAvatarUrl()         { return sellerAvatarUrl; }
    public boolean isSellerVerified()           { return sellerVerified; }
}