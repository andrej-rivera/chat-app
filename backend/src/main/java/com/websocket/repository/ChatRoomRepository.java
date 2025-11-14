package com.websocket.repository;

import java.util.Optional;

import com.websocket.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
