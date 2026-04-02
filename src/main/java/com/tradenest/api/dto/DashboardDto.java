package com.tradenest.api.dto;

public class DashboardDto {

    private int totalProducts;
    private int totalOffersReceived;
    private int totalWishlistCount;
    private int totalReviews;
    private double averageRating;

    public DashboardDto(int totalProducts,
                        int totalOffersReceived,
                        int totalWishlistCount,
                        int totalReviews,
                        double averageRating) {

        this.totalProducts = totalProducts;
        this.totalOffersReceived = totalOffersReceived;
        this.totalWishlistCount = totalWishlistCount;
        this.totalReviews = totalReviews;
        this.averageRating = averageRating;
    }

    public int getTotalProducts() { return totalProducts; }
    public int getTotalOffersReceived() { return totalOffersReceived; }
    public int getTotalWishlistCount() { return totalWishlistCount; }
    public int getTotalReviews() { return totalReviews; }
    public double getAverageRating() { return averageRating; }
}