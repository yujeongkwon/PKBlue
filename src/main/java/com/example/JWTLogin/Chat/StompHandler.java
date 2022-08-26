package com.example.JWTLogin.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    //웹소켓을 통해 들어온 요청이 처리되기전
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        // 헤더 토큰 얻기
        String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
        StompCommand command = headerAccessor.getCommand();
        Long roomId = null;
        if(!command.equals(StompCommand.DISCONNECT)){

            roomId = Long.parseLong(String.valueOf(headerAccessor.getNativeHeader("roomId").get(0)));
        }
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        //웹소켓 연결시 JWT token검증
//        if(accessor.getCommand() == StompCommand.CONNECT) {
//            if(!JwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token")))
//                try {
//                    throw new AccessDeniedException("");
//                } catch (AccessDeniedException e) {
//                    e.printStackTrace();
//                }
//        }

        return message;
    }
}