package com.example.JWTLogin.Chat.dto;

import lombok.Data;

@Data
public class ChatMessageRequestDto {

    private Long roomId;
    private Long userId;
    private String sender;
    private String message;
    private String createdAt;

}