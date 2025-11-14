package com.websocket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "chatrooms")
public class ChatRoom {
    @Id
    private Long id;
    private String roomId;
    private String roomName;
    private Long senderId;
    private Long receiverId;
}
