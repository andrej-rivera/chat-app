package com.websocket.chatroom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
