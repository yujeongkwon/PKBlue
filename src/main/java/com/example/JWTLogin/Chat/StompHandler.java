package com.example.JWTLogin.Chat;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.Chat.service.ChatMessageService;
import com.example.JWTLogin.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatService;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            // websocket 연결요청
            String jwtToken = accessor.getFirstNativeHeader("token");
            // Header의 jwt token 검증
            jwtTokenProvider.validateToken(jwtToken);
        }
        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
            // header정보에서 구독 destination정보를 얻고, roomId를 추출한다.
            String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));

            // 채팅방에 들어온 클라이언트 sessionId를 roomId와 맵핑해 놓는다.(나중에 특정 세션이 어떤 채팅방에 들어가 있는지 알기 위함)
            String sessionId = (String) message.getHeaders().get("simpSessionId");

        }
        return message;
    }
}