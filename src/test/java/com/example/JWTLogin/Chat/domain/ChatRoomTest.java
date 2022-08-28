package com.example.JWTLogin.Chat.domain;

import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChatRoomTest {

    @Autowired ChatRoomRepository chatRoomRepository;

    @Test
    @Transactional
    @Rollback(false)
    void 생성(){
        ChatRoom chatRoom = ChatRoom.builder().build();
        chatRoomRepository.save(chatRoom);
        System.out.println("chatRoom.getId() = " + chatRoom.getRoomId());
    }

    @Test
    void builder() {
    }
}