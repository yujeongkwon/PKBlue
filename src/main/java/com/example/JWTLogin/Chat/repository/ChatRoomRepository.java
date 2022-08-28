package com.example.JWTLogin.Chat.repository;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByRoomId(long roomId);

    ChatRoom findChatRoomByFromMemberIdAndToMemberId(String from_member_id, String to_member_id);


}