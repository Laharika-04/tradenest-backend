package com.tradenest.api.entity;

import com.tradenest.api.enums.ProductCondition;
import com.tradenest.api.enums.ProductStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(columnList = "status"),
        @Index(columnList = "city"),
        @Index(columnList = "featured")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;
    private String city;
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    @Column(length = 3000)
    private String images;

    private boolean featured = false;

    private int viewCount = 0;

    private LocalDateTime postedDate;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    // ── NEW: product condition ────────────────────────────────────────────────
    @Enumerated(EnumType.STRING)
    private ProductCondition condition;

    public Product() {}

    @PrePersist
    public void prePersist() {
        postedDate = LocalDateTime.now();
        status = ProductStatus.ACTIVE;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Long             getId()          { return id; }
    public String           getTitle()       { return title; }
    public String           getDescription() { return description; }
    public BigDecimal       getPrice()       { return price; }
    public String           getCity()        { return city; }
    public String           getState()       { return state; }
    public Category         getCategory()    { return category; }
    public User             getSeller()      { return seller; }
    public String           getImages()      { return images; }
    public boolean          isFeatured()     { return featured; }
    public int              getViewCount()   { return viewCount; }
    public LocalDateTime    getPostedDate()  { return postedDate; }
    public ProductStatus    getStatus()      { return status; }
    public ProductCondition getCondition()   { return condition; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setId(Long id)                        { this.id = id; }
    public void setTitle(String title)                { this.title = title; }
    public void setDescription(String d)              { this.description = d; }
    public void setPrice(BigDecimal price)            { this.price = price; }
    public void setCity(String city)                  { this.city = city; }
    public void setState(String state)                { this.state = state; }
    public void setCategory(Category category)        { this.category = category; }
    public void setSeller(User seller)                { this.seller = seller; }
    public void setImages(String images)              { this.images = images; }
    public void setFeatured(boolean featured)         { this.featured = featured; }
    public void setViewCount(int viewCount)           { this.viewCount = viewCount; }
    public void setStatus(ProductStatus status)       { this.status = status; }
    public void setCondition(ProductCondition cond)   { this.condition = cond; }
}