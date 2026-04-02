package com.tradenest.api.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(columnList = "slug", unique = true),
                @Index(columnList = "name")
        }
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String icon;

    @Column(length = 1000)
    private String description;

    private int productCount = 0;

    public Category() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getIcon() { return icon; }
    public String getDescription() { return description; }
    public int getProductCount() { return productCount; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setDescription(String description) { this.description = description; }
    public void setProductCount(int productCount) { this.productCount = productCount; }
}