package com.example.JWTLogin.Chat.service;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatMessageRequestDto;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.repository.FollowRepository;
import com.example.JWTLogin.service.FollowService;
import com.example.JWTLogin.service.MemberService;
import com.example.JWTLogin.web.dto.member.MemberSignupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class ChatRoomServiceTest {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ChatMessageService chatMessageService;

    private EntityManager em;

    private Member from_member;
    private Member to_member;
    private Member other_member;
    private ChatRoom chatRoom;

    @Rollback(false)
    @BeforeEach
    public void setUp() {
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("skaks1012316@naver.com")
                .password("audwns2021802V!")
                .nickname("명준캡짱")
                .build();

        MemberSignupDto memberSignupDto2 = MemberSignupDto.builder()
                .email("skak6@naver.com")
                .password("audwns20802V!")
                .nickname("부경파랑새")
                .build();

        MemberSignupDto memberSignupDto3 = MemberSignupDto.builder()
                .email("skak61234@naver.com")
                .password("audwns20802V!")
                .nickname("뉴정")
                .build();
        memberService.save(memberSignupDto);
        memberService.save(memberSignupDto2);
        memberService.save(memberSignupDto3);
        from_member = memberService.findByEmail(memberSignupDto.getEmail());
        to_member = memberService.findByEmail(memberSignupDto2.getEmail());
        other_member = memberService.findByEmail(memberSignupDto3.getEmail());

        chatRoom = chatRoomService.createChatRoom(to_member.getNickname(),from_member.getEmail());
    }

    @Test
    @Transactional
    void findAllRooms_성공() {
        ChatRoom chatRoom = chatRoomService.createChatRoom(to_member.getNickname(),from_member.getEmail());
        ChatRoom chatRoom2 = chatRoomService.createChatRoom(other_member.getNickname(),from_member.getEmail());

        System.out.println("chatRoomService = " + chatRoomService.findAllRooms(other_member.getId()));

    }

    @Test
    void 생성_아이디로찾기_성공() {
        ChatRoom chatRoom = chatRoomService.createChatRoom(to_member.getNickname(),from_member.getEmail());
        Assertions.assertThat(chatRoom.getRoomId()).isEqualTo(chatRoomService.findByRoomId(chatRoom.getRoomId()));
    }

    //아 before가있으면 rollback이 작동하지 않는다네유
    @Test
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    void 메세지보내기() {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByFromMemberIdAndToMemberId(to_member.getId(),from_member.getId());
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder()
                .roomId(chatRoom.getRoomId())
                .userId(from_member.getId())
                .message("아니 오재ㅏ꾸 롤백되는겨?? 자동저장해주지않나")
                .build();

        chatMessageService.message(dto);
    }

    @Test
    void getAvailableRoom_성공이겟지() {
        System.out.println("으악이게뭐야"+chatRoomService.getAvailableRoom(to_member.getNickname(),from_member.getEmail()));
    }

    @Test
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    void 메시지가져오기_성공(){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByFromMemberIdAndToMemberId(to_member.getId(),from_member.getId());
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder()
                .roomId(chatRoom.getRoomId())
                .userId(from_member.getId())
                .message("아니 오재ㅏ꾸 롤백되는겨?? 자동저장해주지않나")
                .build();

        ChatMessageRequestDto otherdto = ChatMessageRequestDto.builder()
                .roomId(chatRoom.getRoomId())
                .userId(to_member.getId())
                .message("상대방이 보낸 것")
                .build();

        chatMessageService.message(dto);
        chatMessageService.message(otherdto);
        chatMessageService.message(dto);

        System.out.println("chatMessageService.getAllMessage(chatRoom.getRoomId()) = " + chatMessageService.getAllMessage(chatRoom.getRoomId()));
    }
}