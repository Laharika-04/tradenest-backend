package com.tradenest.api.controller;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.User;
import com.tradenest.api.service.MessageService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("/conversations")
    public ApiResponse<List<ConversationDto>> getConversations(
            @AuthenticationPrincipal User user) {
        return ApiResponse.ok(service.getUserConversations(user.getId()));
    }

    @GetMapping("/conversations/{id}")
    public ApiResponse<List<MessageDto>> getMessages(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ApiResponse.ok(service.getMessages(id, user.getId()));
    }

    @PostMapping("/conversations")
    public ApiResponse<ConversationDto> startConversation(
            @AuthenticationPrincipal User user,
            @RequestBody StartConversationRequest req) {

        return ApiResponse.ok(
                service.startConversation(
                        user.getId(),
                        req.getProductId(),
                        req.getSellerId(),
                        req.getMessage()
                )
        );
    }

    @PostMapping("/conversations/{id}")
    public ApiResponse<MessageDto> sendMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody SendMessageRequest req) {

        return ApiResponse.ok(
                service.sendMessage(id, user.getId(), req.getContent())
        );
    }

    @PutMapping("/conversations/{id}/read")
    public ApiResponse<Object> markRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        service.markAsRead(id, user.getId());
        return ApiResponse.ok(null, "Marked as read");
    }
}