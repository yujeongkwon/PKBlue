package com.example.JWTLogin.Chat.repository;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByRoomId(long roomId);

    @Modifying
    @Query(value = "UPDATE chat_message SET chat_read = 1 WHERE (sender = :other_id AND room_id = :room_id)",nativeQuery = true)
    int readChat(@Param("other_id") long other_id, @Param("room_id") long room_id);

    @Query(value = "SELECT COUNT(chat_message_id) FROM chat_message WHERE (sender = :other_id AND room_id = :room_id) AND chat_read = 0 ", nativeQuery = true )
    int getAllUnreadChat(@Param("other_id") long other_id, @Param("room_id") long room_id);
}
