package com.example.JWTLogin.Chat.repository;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByroomName(String roomName);
}