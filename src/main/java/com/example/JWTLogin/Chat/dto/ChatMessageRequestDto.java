package com.example.JWTLogin.Chat.dto;

import lombok.Data;

@Data
public class ChatMessageRequestDto {

    private String Name;
    private Long userId;
    private String sender;
    private String message;
    private String createdAt;

}