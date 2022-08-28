package com.example.JWTLogin.repository;

import com.example.JWTLogin.Chatdemo.Chat;
import com.example.JWTLogin.Chatdemo.ChatRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatRepositoryTest {

    @Autowired
    ChatRepository chatRepository;
    
    @Test
    @Transactional
    @Rollback(false)
    void 저장(){
        Chat chat =  Chat.builder()
                .fromId(123L)
                .toId(234L)
                .chatContent("안녕 유졍")
                .chatTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);

        System.out.println("chatRepository.findAll() = " + chatRepository.findAll());
        System.out.println("chatRepository.findChatById(123L,234L) = " + chatRepository.findChatById(123L,234L,1));
        System.out.println("chat.getChatId() = " + chat.getChatId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void id로찾기() {
        ArrayList<Chat> chat = chatRepository.findChatById( 123L , 234L, 3);

        for(Chat c:chat){
            System.out.println("c.getChatContent() = " + c.getChatContent());
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    void 최근대화반환() {
        Chat chat =  Chat.builder()
                .fromId(123L)
                .toId(234L)
                .chatContent("안녕 유졍")
                .chatTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);


        chatRepository.submit(123L,234L, "머해 뉴정");
        for (int i=0 ; i <10; i++){
            chatRepository.submit( chat.getFromId() , chat.getToId(), "안농");
        }

        ArrayList<Chat> inchat = chatRepository.findChatByRecent(123L,234L,5);

        for(Chat c:inchat){
            System.out.println("c.getChatContent() = " + c.getChatContent());
        }

    }

    @Test
    @Transactional
    @Rollback(false)
    void 전송() {
        Chat chat =  Chat.builder()
                .fromId(123L)
                .toId(234L)
                .chatContent("안녕 유졍")
                .chatTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);


        chatRepository.submit( chat.getFromId() , chat.getToId(), "안농 뉴정");
        for (int i=0 ; i <10; i++){
            chatRepository.submit( chat.getFromId() , chat.getToId(), "안농");
        }


        ArrayList<Chat> inchat = chatRepository.findChatById(123L,234L,1);

        System.out.println("inchat = " + inchat);
        
        for(Chat c:inchat){
            System.out.println("c.getChatContent() = " + c.getChatContent());
        }


    }


}