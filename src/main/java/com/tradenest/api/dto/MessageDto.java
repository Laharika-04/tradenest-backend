package com.tradenest.api.dto;

import com.tradenest.api.entity.Message;
import java.time.LocalDateTime;

public class MessageDto {

    private Long id;
    private Long senderId;
    private String content;
    private LocalDateTime sentAt;

    public static MessageDto fromEntity(Message m) {
        MessageDto d = new MessageDto();
        d.id = m.getId();
        d.senderId = m.getSender().getId();
        d.content = m.getContent();
        d.sentAt = m.getSentAt();
        return d;
    }

    public Long getId() { return id; }
    public Long getSenderId() { return senderId; }
    public String getContent() { return content; }
    public LocalDateTime getSentAt() { return sentAt; }
}