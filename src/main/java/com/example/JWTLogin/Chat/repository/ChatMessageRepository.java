package com.example.JWTLogin.Chat.repository;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByRoomId(long roomId);

}
