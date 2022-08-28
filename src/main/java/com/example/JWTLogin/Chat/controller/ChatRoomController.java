package com.example.JWTLogin.Chat.controller;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatRoomResponseDto;
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

    //모든 채팅방 목록 반환 -> 나중에 member가 구독한 채팅방만 반환으로 바꾸기
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room(){
        return chatRoomService.findAllRooms();
    }

    //채팅방 생성
    @PostMapping("/room/{otherNickName}")
    public ChatRoom createRoom(@PathVariable String otherNickName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        return chatRoomService.createChatRoom(otherNickName,email);
    }

    //채팅방 상세 조회 또는 생성
    @GetMapping("/room/{otherNickName}")
    public ResponseEntity<ChatRoomResponseDto> getRoomDetail(@PathVariable String otherNickName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        return chatRoomService.getAvailableRoom(otherNickName,email);
    }

    //채팅방 메시지 전체 조회
    @GetMapping("/room/{roomId}/message")
    public List<ChatMessage> getAllMessageOfRoom(@PathVariable Long roomId) {
        return chatMessageService.getAllMessage(roomId);
    }

}
