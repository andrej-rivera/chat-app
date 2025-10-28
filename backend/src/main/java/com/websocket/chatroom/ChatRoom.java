package com.websocket.chatroom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "chatroom")
public class ChatRoom {
    @Id
    private String id;
    private String roomId;
    private String roomName;
    private String senderId;
    private String receiverId;
}
