package com.websocket.service;

import java.util.Optional;

import com.websocket.repository.ChatRoomRepository;
import com.websocket.model.ChatRoom;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository repository;

    // Given the IDs of two users, find the ID of the chatroom between both of them
    // if createIfNotExists == true, then we create a new room if we cant find the roomId in repository
    public Optional<String> getChatRoomId(Long senderId, Long receiverId, boolean createIfNotExists) {
        return repository.findBySenderIdAndReceiverId(senderId, receiverId)
        .map(ChatRoom::getRoomId)
        .or(() -> { // if we dont find the chatroom, create one
            if (createIfNotExists) {
                var roomId = createChat(senderId, receiverId);
            }
            return Optional.empty();
        });
    }

    // Method to create a chatroom between two users
    private String createChat(Long senderId, Long receiverId) {
        // each one-one connection between two users is actually comprised of two separate chatrooms
        var roomId = String.format("%s_%s", senderId, receiverId);
        
        
        // suppose we have users A & B
        // we create a chatroom so user A can send messsages to user B
        ChatRoom senderReceiver = ChatRoom.builder()
            .roomId(roomId)
            .senderId(senderId)
            .receiverId(receiverId)
            .build();

        // next, we create a chatroom so user B can send messsages to user A
        ChatRoom receiverSender = ChatRoom.builder()
            .roomId(roomId)
            .senderId(receiverId)
            .receiverId(senderId)
            .build();
        
        // finally, we save the rooms to our repository
        repository.save(senderReceiver);
        repository.save(receiverSender);

        // both chatrooms are connected via a singular roomId
        return roomId;
    }
}
