package com.tradenest.api.service;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.*;
import com.tradenest.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDto> getSellerReviews(Long sellerId) {
        return reviewRepository.findBySellerIdOrderByCreatedAtDesc(sellerId)
                .stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto createReview(User reviewer,
                                  Long sellerId,
                                  int rating,
                                  String comment) {

        if (reviewer.getId().equals(sellerId)) {
            throw new RuntimeException("Cannot review yourself");
        }

        if (reviewRepository.findByReviewerIdAndSellerId(reviewer.getId(), sellerId).isPresent()) {
            throw new RuntimeException("Already reviewed this seller");
        }

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Review r = new Review();
        r.setReviewer(reviewer);
        r.setSeller(seller);
        r.setRating(rating);
        r.setComment(comment);

        return ReviewDto.fromEntity(reviewRepository.save(r));
    }

    public ReviewStatsDto getSellerStats(Long sellerId) {

        List<Review> reviews = reviewRepository.findBySellerIdOrderByCreatedAtDesc(sellerId);

        if (reviews.isEmpty()) {
            return new ReviewStatsDto(0, 0, Map.of());
        }

        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);

        Map<Integer, Integer> dist = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            int rating = i;
            dist.put(i, (int) reviews.stream().filter(r -> r.getRating() == rating).count());
        }

        return new ReviewStatsDto(Math.round(avg * 10) / 10.0,
                reviews.size(),
                dist);
    }
}