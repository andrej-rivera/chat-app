package com.websocket.repository;

import java.util.List;

import com.websocket.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String>{
    List<ChatMessage> findByRoomId(String roomId);
}
