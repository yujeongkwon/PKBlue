package com.example.JWTLogin.Chat.service;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatRoomResponse;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.handler.CustomValidationException;
import com.example.JWTLogin.repository.MemberRepository;
import com.example.JWTLogin.web.dto.member.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    //채팅방 전체 조회
    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    //채팅방 생성
    public ChatRoom createChatRoom(long id, String email,Long otherId) {
        MemberProfileDto memberProfileDto = new MemberProfileDto();

        Member nowMember = memberRepository.findById(id).orElseThrow(() -> { return new CustomValidationException("찾을 수 없는 user입니다.");});
        memberProfileDto.setMember(nowMember);

        // loginEmail 활용하여 currentId가 로그인된 사용자 인지 확인
        Member loginMember = memberRepository.findByEmail(email);
        memberProfileDto.setLoginMember(loginMember.getId() == nowMember.getId());

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(String.valueOf(nowMember.getId()+otherId))
                .build();

        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    //고유 id를 통한 채팅방 조회
    public ChatRoom getRoomInfo(Long roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다.")
        );
    }

    //Name 값을 통한 채팅방 조회
    public ChatRoom findByroomName(String roomName) {
        return chatRoomRepository.findByroomName(roomName);
    }

    public void save(ChatRoom updatedRoom) {
        chatRoomRepository.save(updatedRoom);
    }

}