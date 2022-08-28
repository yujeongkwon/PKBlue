package com.example.JWTLogin.Chat.domain;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChatMessageTest {
    @Test
    @Transactional
    @Rollback(false)
    void 생성(){
        ChatMessage message = ChatMessage.builder()
                .roomId(234L)
                .sender("sef")
                .message("df")
                .createdAt("sdf")
                .build();
//        chatRoomRepository.save(chatRoom);
//        System.out.println("chatRoom.getId() = " + chatRoom.getRoomId());
    }
}