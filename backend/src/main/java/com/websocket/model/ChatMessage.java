package com.websocket.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatmessages")
public class ChatMessage {
    @Id
    private Long id;
    private String roomId;

    private Long senderId;
    private Long receiverId;
    private String content;

    private Date timeStamp;
}
