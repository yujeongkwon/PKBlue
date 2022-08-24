package com.example.JWTLogin.service;

import com.example.JWTLogin.handler.CustomApiException;
import com.example.JWTLogin.repository.ChatRepository;
import com.example.JWTLogin.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final EntityManager em;
    private final ChatRepository chatRepository;

    //submit
    @Transactional
    public void submit(long fromId, long toId, String chatContent){
        chatRepository.submit( fromId , toId, chatContent);
    }



}
