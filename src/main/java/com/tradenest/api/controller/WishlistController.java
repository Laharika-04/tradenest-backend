package com.tradenest.api.controller;

import com.tradenest.api.dto.WishlistDto;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.WishlistService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<WishlistDto>> getWishlist(
            @AuthenticationPrincipal User user) {

        return ApiResponse.ok(service.getWishlist(user.getId()));
    }

    @PostMapping("/{productId}")
    public ApiResponse<Object> add(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId) {

        service.addToWishlist(user.getId(), productId);
        return ApiResponse.ok(null, "Added to wishlist");
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Object> remove(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId) {

        service.removeFromWishlist(user.getId(), productId);
        return ApiResponse.ok(null, "Removed");
    }

    @GetMapping("/check/{productId}")
    public ApiResponse<Boolean> check(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId) {

        return ApiResponse.ok(service.isWishlisted(user.getId(), productId));
    }
}