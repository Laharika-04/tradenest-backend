package com.tradenest.api.controller;

import com.tradenest.api.dto.NotificationDto;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.NotificationService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<NotificationDto>> getAll(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(service.getForUser(user.getId()));
    }

    @GetMapping("/unread-count")
    public ApiResponse<Map<String, Integer>> unreadCount(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(Map.of("count", service.getUnreadCount(user.getId())));
    }

    @PutMapping("/mark-all-read")
    public ApiResponse<Object> markAllRead(@AuthenticationPrincipal User user) {
        service.markAllRead(user.getId());
        return ApiResponse.ok(null, "All marked as read");
    }

    @PutMapping("/{id}/read")
    public ApiResponse<Object> markOneRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        service.markOneRead(id, user.getId());
        return ApiResponse.ok(null, "Marked as read");
    }
}