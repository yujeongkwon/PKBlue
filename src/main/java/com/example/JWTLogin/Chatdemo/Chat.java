package com.example.JWTLogin.Chatdemo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column
    private Long fromId;    //채팅 보내는사람

    @Column
    private Long toId;      //채팅 받는 사람

    @Column
    private String chatContent;

    @Column
    private LocalDateTime chatTime;

    @Column
    private int chatRead;

    @Builder
    public Chat(Long chatId,Long fromId, Long toId, String chatContent, LocalDateTime chatTime, int chatRead) {
        this.chatId = chatId;
        this.fromId = fromId;
        this.toId = toId;
        this.chatContent = chatContent;
        this.chatTime = chatTime;
        this.chatRead = chatRead;
    }
}
