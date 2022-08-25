package com.example.JWTLogin.Chat;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
@AllArgsConstructor
@Data
public class ChatDto {
    private Long fromId;    //채팅 보내는사람
    private Long toId;      //채팅 받는 사람
    private String chatContent;
    private LocalDateTime chatTime;

}
