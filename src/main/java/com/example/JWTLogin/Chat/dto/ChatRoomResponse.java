package com.example.JWTLogin.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ChatRoomResponse {

    private String roomId;
    private int user_count;
    private String message;

}