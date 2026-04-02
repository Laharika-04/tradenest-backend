package com.tradenest.api.dto;

import com.tradenest.api.entity.Review;
import java.time.LocalDateTime;

public class ReviewDto {

    private Long id;
    private Long reviewerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewDto fromEntity(Review r) {
        ReviewDto d = new ReviewDto();
        d.id = r.getId();
        d.reviewerId = r.getReviewer().getId();
        d.rating = r.getRating();
        d.comment = r.getComment();
        d.createdAt = r.getCreatedAt();
        return d;
    }

    public Long getId() { return id; }
    public Long getReviewerId() { return reviewerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}