package com.tradenest.api.service;

import com.tradenest.api.dto.DashboardDto;
import com.tradenest.api.entity.Review;
import com.tradenest.api.enums.ProductStatus;
import com.tradenest.api.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final ProductRepository  productRepository;
    private final OfferRepository    offerRepository;
    private final WishlistRepository wishlistRepository;
    private final ReviewRepository   reviewRepository;

    public DashboardService(ProductRepository productRepository,
                            OfferRepository offerRepository,
                            WishlistRepository wishlistRepository,
                            ReviewRepository reviewRepository) {
        this.productRepository  = productRepository;
        this.offerRepository    = offerRepository;
        this.wishlistRepository = wishlistRepository;
        this.reviewRepository   = reviewRepository;
    }

    public DashboardDto getSellerDashboard(Long sellerId) {

        // FIX: was loading ALL products just to call .size()
        // Use a count query in the repository instead (see ProductRepository fix)
        int totalProducts = productRepository.countBySellerId(sellerId);

        int totalOffers   = offerRepository.countByProductSellerId(sellerId);
        int totalWishlist = wishlistRepository.countByProductSellerId(sellerId);

        List<Review> reviews = reviewRepository.findBySellerIdOrderByCreatedAtDesc(sellerId);
        int totalReviews = reviews.size();

        double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        avgRating = Math.round(avgRating * 10) / 10.0;

        return new DashboardDto(
                totalProducts,
                totalOffers,
                totalWishlist,
                totalReviews,
                avgRating
        );
    }
}