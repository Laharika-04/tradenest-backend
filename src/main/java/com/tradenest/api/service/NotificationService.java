package com.tradenest.api.service;

import com.tradenest.api.dto.NotificationDto;
import com.tradenest.api.entity.Notification;
import com.tradenest.api.entity.User;
import com.tradenest.api.enums.NotificationType;
import com.tradenest.api.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public List<NotificationDto> getForUser(Long userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public int getUnreadCount(Long userId) {
        return repo.countByUserIdAndReadFalse(userId);
    }

    @Transactional
    public void markAllRead(Long userId) {
        List<Notification> unread = repo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .filter(n -> !n.isRead())
                .collect(Collectors.toList());
        unread.forEach(n -> n.setRead(true));
        repo.saveAll(unread);
    }

    @Transactional
    public void markOneRead(Long notificationId, Long userId) {
        repo.findById(notificationId).ifPresent(n -> {
            if (n.getUser().getId().equals(userId)) {
                n.setRead(true);
                repo.save(n);
            }
        });
    }

    // ── Helper called by other services to push notifications ────────────────

    @Transactional
    public void push(User user, String title, String message,
                     NotificationType type, Long referenceId) {
        Notification n = new Notification();
        n.setUser(user);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setReferenceId(referenceId);
        repo.save(n);
    }
}