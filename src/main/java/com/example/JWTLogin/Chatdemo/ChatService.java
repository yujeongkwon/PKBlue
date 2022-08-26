package com.example.JWTLogin.Chatdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final EntityManager em;
    private final ChatRepository chatRepository;

    //ChatId로찾기
    @Transactional
    public ArrayList<Chat> findChatById(long fromId, long toId, long chatId){
        ArrayList<Chat> chat = chatRepository.findChatById( fromId , toId, chatId);
        return chat;
    }

    //최근대화부르기
    @Transactional
    public ArrayList<Chat> findChatByRecent(long fromId, long toId, int number){
        ArrayList<Chat> chat = chatRepository.findChatByRecent( fromId , toId, number);
        return chat;
    }

    //submit
    @Transactional
    public void submit(long fromId, long toId, String chatContent){
         chatRepository.submit( fromId , toId, chatContent);
    }

    //다읽음 처리
    @Transactional
    public void readChat(long fromId, long toId){
        chatRepository.readChat(fromId , toId);
    }

    //안읽은 챗 개수
    @Transactional
    public void getAllUnreadChat(long userId){
        chatRepository.getAllUnreadChat(userId);
    }

}
