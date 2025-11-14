package com.websocket.controller;

import java.util.List;

import com.websocket.model.ChatMessage;
import com.websocket.service.ChatMessageService;
import com.websocket.model.ChatNotification;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService service;

    @GetMapping("/chat/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId") Long senderId,
                                                              @PathVariable("receiverId") Long receiverId) {
        return ResponseEntity.ok(service.findChatMessages(senderId, receiverId));
    }
    
    // Method to send message from sender to receiver
    public void processMessage(@Payload ChatMessage message) {
        ChatMessage savedMessage = service.save(message);

        // we want to notify the receiver of the new message via a notification
        ChatNotification notification = ChatNotification.builder()
            .id(savedMessage.getId())
            .senderId(savedMessage.getSenderId())
            .receiverId(savedMessage.getReceiverId())
            .content(savedMessage.getContent())
            .build();

        messagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverId()), "/queue/messages", notification);

    }
}
