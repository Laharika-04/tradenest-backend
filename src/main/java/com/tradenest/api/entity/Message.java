package com.tradenest.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @Column(length = 2000)
    private String content;

    private LocalDateTime sentAt;
    private boolean read;

    public Message() {}

    @PrePersist
    public void prePersist() {
        sentAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Conversation getConversation() { return conversation; }
    public User getSender() { return sender; }
    public String getContent() { return content; }
    public LocalDateTime getSentAt() { return sentAt; }
    public boolean isRead() { return read; }

    public void setConversation(Conversation conversation) { this.conversation = conversation; }
    public void setSender(User sender) { this.sender = sender; }
    public void setContent(String content) { this.content = content; }
    public void setRead(boolean read) { this.read = read; }
}