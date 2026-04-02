package com.tradenest.api.dto;

import com.tradenest.api.enums.ProductCondition;

import java.math.BigDecimal;

/**
 * FIX: ProductController was accepting a raw Product entity which exposes internal
 * fields (id, seller, status, viewCount, postedDate) to client manipulation.
 * Use this DTO instead for POST /api/products and PUT /api/products/{id}.
 */
public class CreateProductRequest {

    private String           title;
    private String           description;
    private BigDecimal       price;
    private String           city;
    private String           state;
    private String           images;
    private boolean          featured;
    private ProductCondition condition;
    private CategoryRef      category;

    // Inner class to accept { "id": 3 } from frontend
    public static class CategoryRef {
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    public String           getTitle()       { return title; }
    public String           getDescription() { return description; }
    public BigDecimal       getPrice()       { return price; }
    public String           getCity()        { return city; }
    public String           getState()       { return state; }
    public String           getImages()      { return images; }
    public boolean          isFeatured()     { return featured; }
    public ProductCondition getCondition()   { return condition; }
    public CategoryRef      getCategory()    { return category; }

    public void setTitle(String title)                  { this.title = title; }
    public void setDescription(String description)      { this.description = description; }
    public void setPrice(BigDecimal price)              { this.price = price; }
    public void setCity(String city)                    { this.city = city; }
    public void setState(String state)                  { this.state = state; }
    public void setImages(String images)                { this.images = images; }
    public void setFeatured(boolean featured)           { this.featured = featured; }
    public void setCondition(ProductCondition condition){ this.condition = condition; }
    public void setCategory(CategoryRef category)       { this.category = category; }
}