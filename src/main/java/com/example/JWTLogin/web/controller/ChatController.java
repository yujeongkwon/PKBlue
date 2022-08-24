package com.example.JWTLogin.web.controller;

import com.example.JWTLogin.domain.Chat;
import com.example.JWTLogin.repository.ChatRepository;
import com.example.JWTLogin.service.ChatService;
import com.example.JWTLogin.web.dto.ChatDto;
import com.example.JWTLogin.web.dto.member.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    ChatRepository chatRepository;

    @GetMapping("/{toId}")
    public ArrayList<Chat> chatSubmit(@Valid  @RequestParam long fromId, @RequestParam long toId, @RequestParam String chatContent) {
        chatService.submit(fromId,toId,chatContent);

        ArrayList<Chat> inchat = chatRepository.findChatById(123L,234L,1);

        return inchat;
    }
}
