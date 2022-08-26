package com.example.JWTLogin.Chat.controller;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatRoomResponse;
import com.example.JWTLogin.Chat.service.ChatMessageService;
import com.example.JWTLogin.Chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    //채팅방 생성
    @GetMapping("/room/{otherId}")
    public ChatRoom createRoom(@PathVariable Long otherId, @RequestParam long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        return chatRoomService.createChatRoom(id,email,otherId);
    }

    //채팅방 상세 조회
    @GetMapping("/room/{roomName}")
    public ChatRoom getRoomDetail(@PathVariable String roomName) {
        return chatRoomService.findByroomName(roomName);
    }

    //채팅방 메시지 전체 조회
    @GetMapping("/room/{roomName}/message")
    public List<ChatMessage> getAllMessageOfRoom(@PathVariable String roomName) {
        return chatMessageService.getAllMessage(roomName);
    }

}
