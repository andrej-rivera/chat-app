package com.websocket.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.websocket.chatroom.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService service;

    // Save a chat message to the repository
    public ChatMessage save(ChatMessage message) {
        // First, obtain the roomId for the chat between sender & receiver (create a room if none exists)
        var roomId = service.getChatRoomId(message.getSenderId(), message.getReceiverId(), true)
        .orElseThrow(); // todo: throw custom chat error message (can't obtain room id)

        // Next, set the roomId for the message & save it to repository
        message.setRoomId(roomId);
        repository.save(message);
        return message;
    }

    public List<ChatMessage> findChatMessages(String senderId, String receiverId) {
        // First, obtain the roomId for the chat between sender & receiver (no need to create a room here)
        var roomId = service.getChatRoomId(senderId, receiverId, false);

        return roomId.map(repository::findByRoomId).orElse(new ArrayList<>());
    }

}
