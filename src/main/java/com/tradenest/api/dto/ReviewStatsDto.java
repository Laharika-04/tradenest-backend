package com.tradenest.api.dto;

import java.util.Map;

public class ReviewStatsDto {

    private double averageRating;
    private int totalReviews;
    private Map<Integer, Integer> distribution;

    public ReviewStatsDto(double avg, int total, Map<Integer, Integer> dist) {
        this.averageRating = avg;
        this.totalReviews = total;
        this.distribution = dist;
    }

    public double getAverageRating() { return averageRating; }
    public int getTotalReviews() { return totalReviews; }
    public Map<Integer, Integer> getDistribution() { return distribution; }
}