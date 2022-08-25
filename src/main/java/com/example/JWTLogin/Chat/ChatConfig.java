package com.example.JWTLogin.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override   //소켓연결 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //addEndpoint(): 소켓연결 URL ㅣsetAllowedOriginPatterns: CORS설정 | withSockJS() : 소켓 지원하지 않는 브라우저=sockJS로
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*").withSockJS();
    }

    @Override   //Stomp 사용위한 Message Broker설정
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //메세지 받을 때, 경로 설정 / messageBroker로 보냄   1:1
        registry.enableSimpleBroker("/queue");
        //메세지 보낼 때, 경로 설정 /Broker로 보냄
        registry.setApplicationDestinationPrefixes("/app");
    }
// StompHandler가 웹 소켓 앞단에서 token, 메시지, TYPE 등 체크할 수 있도록
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}