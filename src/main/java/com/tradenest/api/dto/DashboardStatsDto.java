package com.tradenest.api.dto;

public record DashboardStatsDto(
        int totalAds,
        int activeAds,
        int totalViews,
        int totalWishlistSaves,
        int totalOffers,
        int totalMessages
) {}