package com.example.JWTLogin.Chatdemo;

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

    @PostMapping("/{toId}")
    public ArrayList<Chat> chatSubmit(@Valid  @RequestParam long fromId, @RequestParam long toId, @RequestParam String chatContent) {
        chatService.submit(fromId,toId,chatContent);
        return chatService.findChatById(123L,234L,1);
    }

    //fromid받는거는 난주에
    @GetMapping("/{toId}")
    public ArrayList<Chat> chatRecent(@Valid @PathVariable("toId") Long toId, @RequestParam long fromId){
        chatService.readChat(fromId, toId);
        return chatService.findChatByRecent(fromId,toId,100);
    }

//    //채팅홈
//    @GetMapping("/{toId}")

}
