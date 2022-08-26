package com.example.JWTLogin.Chatdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Init {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final ChatRepository chatRepository;

        public void dbInit() {

            Chat chat =  Chat.builder()
                    .fromId(123L)
                    .toId(234L)
                    .chatContent("안녕 유졍")
                    .chatTime(LocalDateTime.now())
                    .chatRead(0)
                    .build();

            em.persist(chat);

            chatRepository.submit(123L,234L, "머해 뉴정");
            for (int i=0 ; i <10; i++){
                chatRepository.submit( chat.getFromId() , chat.getToId(), "안농");
            }

        }
    }


}
