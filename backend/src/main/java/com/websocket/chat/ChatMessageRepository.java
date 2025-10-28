package com.websocket.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String>{
    List<ChatMessage> findByRoomId(String roomId);
}
