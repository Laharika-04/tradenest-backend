package com.tradenest.api.repository;

import com.tradenest.api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySellerIdOrderByCreatedAtDesc(Long sellerId);

    Optional<Review> findByReviewerIdAndSellerId(Long reviewerId, Long sellerId);
}