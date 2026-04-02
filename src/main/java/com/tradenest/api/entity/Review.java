package com.tradenest.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews",
       uniqueConstraints = @UniqueConstraint(
               columnNames = {"reviewer_id", "seller_id"}
       ))
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    private int rating;

    @Column(length = 1000)
    private String comment;

    private LocalDateTime createdAt;

    public Review() {}

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getReviewer() { return reviewer; }
    public User getSeller() { return seller; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setReviewer(User reviewer) { this.reviewer = reviewer; }
    public void setSeller(User seller) { this.seller = seller; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}