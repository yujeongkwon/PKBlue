package com.example.JWTLogin.Chatdemo;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class ChatDto {
    private Long fromId;    //채팅 보내는사람
    private Long toId;      //채팅 받는 사람
    private String chatContent;
    private LocalDateTime chatTime;

}
