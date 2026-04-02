package com.tradenest.api.controller;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.OfferService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService service;

    public OfferController(OfferService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<OfferDto> makeOffer(
            @AuthenticationPrincipal User user,
            @RequestBody MakeOfferRequest req) {

        return ApiResponse.ok(
                service.makeOffer(user, req.getProductId(), req.getAmount(), req.getMessage())
        );
    }

    @GetMapping("/mine")
    public ApiResponse<List<OfferDto>> myOffers(
            @AuthenticationPrincipal User user) {

        return ApiResponse.ok(service.getMyOffers(user.getId()));
    }

    @GetMapping("/received")
    public ApiResponse<List<OfferDto>> received(
            @AuthenticationPrincipal User user) {

        return ApiResponse.ok(service.getReceivedOffers(user.getId()));
    }

    @PutMapping("/{id}/accept")
    public ApiResponse<OfferDto> accept(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        return ApiResponse.ok(service.accept(id, user.getId()));
    }

    @PutMapping("/{id}/reject")
    public ApiResponse<OfferDto> reject(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        return ApiResponse.ok(service.reject(id, user.getId()));
    }

    @PutMapping("/{id}/counter")
    public ApiResponse<OfferDto> counter(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody CounterOfferRequest req) {

        return ApiResponse.ok(service.counter(id, user.getId(), req.getAmount()));
    }
}