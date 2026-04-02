package com.tradenest.api.dto;

import com.tradenest.api.entity.Notification;
import com.tradenest.api.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationDto {

    private Long             id;
    private String           title;
    private String           message;
    private NotificationType type;
    private Long             referenceId;
    private boolean          read;
    private LocalDateTime    createdAt;

    public static NotificationDto fromEntity(Notification n) {
        NotificationDto d = new NotificationDto();
        d.id          = n.getId();
        d.title       = n.getTitle();
        d.message     = n.getMessage();
        d.type        = n.getType();
        d.referenceId = n.getReferenceId();
        d.read        = n.isRead();
        d.createdAt   = n.getCreatedAt();
        return d;
    }

    public Long             getId()          { return id; }
    public String           getTitle()       { return title; }
    public String           getMessage()     { return message; }
    public NotificationType getType()        { return type; }
    public Long             getReferenceId() { return referenceId; }
    public boolean          isRead()         { return read; }
    public LocalDateTime    getCreatedAt()   { return createdAt; }
}