package com.example.JWTLogin.Chat.controller;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatRoomResponseDto;
import com.example.JWTLogin.Chat.service.ChatMessageService;
import com.example.JWTLogin.Chat.service.ChatRoomService;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.repository.MemberRepository;
import com.example.JWTLogin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;

    // member가 구독한 채팅방만 반환으로 바꾸기
    @GetMapping("/rooms")
    @ResponseBody
    public ArrayList<ChatRoom> room(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        Member fromMember = memberService.findByEmail(email); // 현재 본인
        return chatRoomService.findAllRooms(fromMember.getId());
    }
    

    //채팅방 생성
    @PostMapping("/room/{otherNickName}")
    public ChatRoom createRoom(@PathVariable String otherNickName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        return chatRoomService.createChatRoom(otherNickName,email);
    }

    //채팅방 생성 또는 이미 방이있다면 배정
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
