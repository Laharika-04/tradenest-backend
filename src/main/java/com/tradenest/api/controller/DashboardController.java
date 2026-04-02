package com.tradenest.api.controller;

import com.tradenest.api.dto.DashboardDto;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.DashboardService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<DashboardDto> dashboard(
            @AuthenticationPrincipal User user) {

        return ApiResponse.ok(
                service.getSellerDashboard(user.getId())
        );
    }
}