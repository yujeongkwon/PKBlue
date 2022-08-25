package com.example.JWTLogin.Chat;

import com.example.JWTLogin.Chat.Chat;
import com.example.JWTLogin.Chat.ChatRepository;
import com.example.JWTLogin.Chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    ChatRepository chatRepository;

    @PostMapping("/{toId}")
    @MessageMapping("/chat/submit")
    public ArrayList<Chat> chatSubmit(@Valid  @RequestParam long fromId, @RequestParam long toId, @RequestParam String chatContent) {
        chatService.submit(fromId,toId,chatContent);

        ArrayList<Chat> inchat = chatRepository.findChatById(123L,234L,1);

        //구독한 링크로 전송 pub는 모두 동일한 링크 / sub를 개인의 고유한 url = 1:1
//        simpMessagingTemplate.convertAndSend("/topic/" + receiver,message);
        return inchat;
    }

    //fromid받는거는 = 로그인된사람 난주에
    @GetMapping("/{toId}")
    public ArrayList<Chat> chatRecent(@Valid @PathVariable("toId") Long toId, @RequestParam long fromId){
        return chatService.findChatByRecent(fromId,toId,5);
    }
}
