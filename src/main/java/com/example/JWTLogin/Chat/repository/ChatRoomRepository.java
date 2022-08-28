package com.example.JWTLogin.Chat.repository;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chatdemo.Chat;
import com.example.JWTLogin.domain.Follow;
import com.example.JWTLogin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByRoomIdOrderByRoomIdDesc(long roomId);

    ChatRoom findByRoomId(long roomId);

    @Query(value = "SELECT * FROM chat_room WHERE ((from_member_id = :from_member_id AND to_member_id = :to_member_id) OR (from_member_id = :to_member_id AND to_member_id = :from_member_id)) ", nativeQuery = true)
    ChatRoom findChatRoomByFromMemberIdAndToMemberId(@Param("from_member_id") long from_member_id,@Param("to_member_id") long to_member_id);

//    ChatRoom findChatRoomByFromMemberIdAndToMemberId(String from_member_id, String to_member_id);

    @Query(value = "SELECT * FROM chat_room WHERE from_member_id = :memberId OR to_member_id = :memberId", nativeQuery = true)
    ArrayList<ChatRoom> findMyRooms(@Param("memberId") long memberId);
}