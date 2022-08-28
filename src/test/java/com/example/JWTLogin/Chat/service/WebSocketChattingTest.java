//package com.example.JWTLogin.Chat.service;
//
//import com.example.JWTLogin.Chat.domain.ChatRoom;
//import com.example.JWTLogin.Chat.dto.ChatMessageRequestDto;
//import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
//import com.example.JWTLogin.domain.Member;
//import com.example.JWTLogin.repository.MemberRepository;
//import com.example.JWTLogin.service.MemberService;
//import com.example.JWTLogin.web.dto.member.MemberSignupDto;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//import org.springframework.web.socket.sockjs.client.SockJsClient;
//import org.springframework.web.socket.sockjs.client.Transport;
//import org.springframework.web.socket.sockjs.client.WebSocketTransport;
//
//import javax.persistence.EntityManager;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WebSocketChattingTest {
//
//    @LocalServerPort
//    private int port;
//    //소켓은 스레드에서 동작 -> BlockingQueue 에 담아둠
//    private BlockingQueue<ChatMessageRequestDto> users;
//    private BlockingQueue<ChatMessageRequestDto> messages;
//    @Autowired
//    private MemberRepository userRepository;
//    @Autowired
//    private ChatRoomRepository roomRepository;
//    @Autowired
//    private ChatRoomService chatRoomService;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private ChatMessageService chatMessageService;
//
//    private Member from_member;
//    private Member to_member;
//    private Member other_member;
//
//    private ChatRoom room;
//    private ChatRoom room2;
//
////    @BeforeEach
////    public void setUp() {
////        RestAssured.port = port;
////        users = new LinkedBlockingDeque<>();
////        messages = new LinkedBlockingDeque<>();
////        유저_삽입();
////        방_생성();
////    }
//
//    @BeforeEach
//    private void 유저_삽입() {
//        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
//                .email("skaks1012316@naver.com")
//                .password("audwns2021802V!")
//                .nickname("명준캡짱")
//                .build();
//
//        MemberSignupDto memberSignupDto2 = MemberSignupDto.builder()
//                .email("skak6@naver.com")
//                .password("audwns20802V!")
//                .nickname("부경파랑새")
//                .build();
//
//        MemberSignupDto memberSignupDto3 = MemberSignupDto.builder()
//                .email("skak61234@naver.com")
//                .password("audwns20802V!")
//                .nickname("뉴정")
//                .build();
//        memberService.save(memberSignupDto);
//        memberService.save(memberSignupDto2);
//        memberService.save(memberSignupDto3);
//        from_member = memberService.findByEmail(memberSignupDto.getEmail());
//        to_member = memberService.findByEmail(memberSignupDto2.getEmail());
//        other_member = memberService.findByEmail(memberSignupDto3.getEmail());
//    }
//
//    @BeforeEach
//    private void 방_생성() {
//        ChatRoom room = chatRoomService.createChatRoom(to_member.getNickname(),from_member.getEmail());
//        ChatRoom room2 = chatRoomService.createChatRoom(other_member.getNickname(),from_member.getEmail());
//    }
//
//    @DisplayName("유저가 입장하고 메시지를 보내면 해당 방에 메시지가 브로드 캐스팅된다.")
//    @Test
//    void enterUserAndBroadCastMessage() throws InterruptedException, ExecutionException, TimeoutException {
//        // Settings
//        WebSocketStompClient webSocketStompClient = 웹_소켓_STOMP_CLIENT();
//        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        // Connection
//        ListenableFuture<StompSession> connect = webSocketStompClient
//                .connect("ws://localhost:8080/ws-connection", new StompSessionHandlerAdapter() {
//                });
//        StompSession stompSession = connect.get(60, TimeUnit.SECONDS);
//
//        stompSession.subscribe(String.format("/sub/chat/room/%s", room.getRoomId()), new StompFrameHandlerImpl(new ChatMessageRequestDto(), users));
//        stompSession.send(String.format("/pub/rooms/%s", room.getRoomId()), new SessionRequest(user.getId(), "1A2B3C4D"));
//
//        stompSession.subscribe(String.format("/sub/rooms/%s/chat", room.getRoomId()), new StompFrameHandlerImpl(new ChatMessageRequestDto(), messages));
//        stompSession.send(String.format("/sub/rooms/%s/chat", room.getRoomId()), new MessageRequest(user.getId(), "채팅을 보내 봅니다."));
//
//        ChatMessageRequestDto response = messages.poll(5, TimeUnit.SECONDS);
//
//        // Then
//        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(expected);
//    }
//
//    private WebSocketStompClient 웹_소켓_STOMP_CLIENT() {
//        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
//        WebSocketTransport webSocketTransport = new WebSocketTransport((WebSocketClient) standardWebSocketClient);
//        List<Transport> transports = Collections.singletonList(webSocketTransport);
//        SockJsClient sockJsClient = new SockJsClient(transports);
//
//        return new WebSocketStompClient(sockJsClient);
//    }
//}