package com.tradenest.api.controller;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.ReviewService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/{sellerId}")
    public ApiResponse<List<ReviewDto>> getSellerReviews(
            @PathVariable Long sellerId) {
        return ApiResponse.ok(service.getSellerReviews(sellerId));
    }

    @PostMapping
    public ApiResponse<ReviewDto> createReview(
            @AuthenticationPrincipal User user,
            @RequestBody CreateReviewRequest req) {

        return ApiResponse.ok(
                service.createReview(user,
                        req.getSellerId(),
                        req.getRating(),
                        req.getComment())
        );
    }

    @GetMapping("/{sellerId}/stats")
    public ApiResponse<ReviewStatsDto> stats(
            @PathVariable Long sellerId) {

        return ApiResponse.ok(service.getSellerStats(sellerId));
    }
}