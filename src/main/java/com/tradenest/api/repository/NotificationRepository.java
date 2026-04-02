package com.tradenest.api.repository;

import com.tradenest.api.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    int countByUserIdAndReadFalse(Long userId);

}