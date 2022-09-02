package com.example.JWTLogin.Chat;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.domain.EnterInfo;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.Chat.repository.EnterInfoRepository;
import com.example.JWTLogin.Chat.service.ChatMessageService;
import com.example.JWTLogin.Chat.service.ChatRoomService;
import com.example.JWTLogin.config.security.JwtTokenProvider;
import com.example.JWTLogin.service.MemberService;
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
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final MemberService memberService;
    private final EnterInfoRepository enterInfoRepository;

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
            long roomId = Long.parseLong(chatMessageService.getRoomId(Optional.ofNullable(
                            (String) message.getHeaders().get("simpDestination"))
                    .orElse("Anonymous User")));

            String sessionId = (String) message.getHeaders().get("simpSessionId");
            enterInfoRepository.save(EnterInfo.builder()
                    .sessionId(sessionId)
                    .roomId(roomId)
                    .build());
        }
        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
        }

        return message;
    }
}