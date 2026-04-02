package com.tradenest.api.dto;

public class CreateReviewRequest {

    private Long sellerId;
    private int rating;
    private String comment;

    public Long getSellerId() { return sellerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}