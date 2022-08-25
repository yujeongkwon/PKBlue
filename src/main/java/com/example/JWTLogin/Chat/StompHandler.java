package com.example.JWTLogin.Chat;

import com.example.JWTLogin.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    //웹소켓을 통해 들어온 요청이 처리되기전
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        //웹소켓 연결시 JWT token검증
        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(!JwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token")))
                try {
                    throw new AccessDeniedException("");
                } catch (AccessDeniedException e) {
                    e.printStackTrace();
                }
        }

        return message;
    }
}