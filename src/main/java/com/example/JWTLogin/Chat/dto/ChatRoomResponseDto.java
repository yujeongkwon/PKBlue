package com.example.JWTLogin.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private Long roomId;
    //나중에 안읽은 갯수 표시
}