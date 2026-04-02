package com.tradenest.api.controller;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.AuthService;
import com.tradenest.api.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ApiResponse.ok(service.register(req));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest req) {
        return ApiResponse.ok(service.login(req));
    }

    @GetMapping("/profile")
    public ApiResponse<UserDto> profile(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(service.getProfile(user.getId()));
    }

    // ── NEW: update logged-in user's profile ─────────────────────────────────
    @PutMapping("/profile")
    public ApiResponse<UserDto> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateProfileRequest req) {
        return ApiResponse.ok(service.updateProfile(user.getId(), req));
    }
}