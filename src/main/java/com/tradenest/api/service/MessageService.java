package com.tradenest.api.service;

import com.tradenest.api.dto.*;
import com.tradenest.api.entity.*;
import com.tradenest.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public MessageService(ConversationRepository conversationRepository,
                          MessageRepository messageRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // ✅ Get all conversations of logged user
    public List<ConversationDto> getUserConversations(Long userId) {

        return conversationRepository
                .findByBuyerIdOrSellerIdOrderByLastMessageAtDesc(userId, userId)
                .stream()
                .map(ConversationDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ✅ Get messages of conversation
    public List<MessageDto> getMessages(Long conversationId, Long userId) {

        Conversation conv = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        validateParticipant(conv, userId);

        return messageRepository.findByConversationIdOrderBySentAtAsc(conversationId)
                .stream()
                .map(MessageDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ✅ Start conversation (OLX style)
    @Transactional
    public ConversationDto startConversation(Long buyerId,
                                             Long productId,
                                             Long sellerId,
                                             String message) {

        if (buyerId.equals(sellerId)) {
            throw new RuntimeException("Cannot start conversation with yourself");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User buyer = userRepository.findById(buyerId).orElseThrow();
        User seller = userRepository.findById(sellerId).orElseThrow();

        // ✅ Prevent duplicate conversation
        Conversation conv = conversationRepository
                .findByBuyerIdAndSellerIdAndProductId(buyerId, sellerId, productId)
                .orElse(null);

        if (conv == null) {
            conv = new Conversation();
            conv.setBuyer(buyer);
            conv.setSeller(seller);
            conv.setProduct(product);
            conv.setBuyerUnread(0);
            conv.setSellerUnread(1);
        }

        conv.setLastMessage(message);
        conv.setLastMessageAt(LocalDateTime.now());

        conv = conversationRepository.save(conv);

        Message msg = new Message();
        msg.setConversation(conv);
        msg.setSender(buyer);
        msg.setContent(message);
        msg.setRead(false);

        messageRepository.save(msg);

        return ConversationDto.fromEntity(conv);
    }

    // ✅ Send message
    @Transactional
    public MessageDto sendMessage(Long conversationId,
                                  Long senderId,
                                  String content) {

        Conversation conv = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        validateParticipant(conv, senderId);

        User sender = userRepository.findById(senderId).orElseThrow();

        Message msg = new Message();
        msg.setConversation(conv);
        msg.setSender(sender);
        msg.setContent(content);
        msg.setRead(false);

        msg = messageRepository.save(msg);

        conv.setLastMessage(content);
        conv.setLastMessageAt(LocalDateTime.now());

        if (senderId.equals(conv.getBuyer().getId())) {
            conv.setSellerUnread(conv.getSellerUnread() + 1);
        } else {
            conv.setBuyerUnread(conv.getBuyerUnread() + 1);
        }

        conversationRepository.save(conv);

        return MessageDto.fromEntity(msg);
    }

    // ✅ Mark as read
    @Transactional
    public void markAsRead(Long conversationId, Long userId) {

        Conversation conv = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        validateParticipant(conv, userId);

        if (userId.equals(conv.getBuyer().getId())) {
            conv.setBuyerUnread(0);
        } else {
            conv.setSellerUnread(0);
        }

        conversationRepository.save(conv);
    }

    // 🔐 SECURITY VALIDATION
    private void validateParticipant(Conversation conv, Long userId) {

        if (!userId.equals(conv.getBuyer().getId())
                && !userId.equals(conv.getSeller().getId())) {
            throw new RuntimeException("Unauthorized access to conversation");
        }
    }
}