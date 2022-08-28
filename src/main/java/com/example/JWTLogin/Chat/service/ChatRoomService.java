package com.example.JWTLogin.Chat.service;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatRoomResponseDto;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public ChatRoom createChatRoom(String otherNickName, String email) {
        Member loginMember = memberRepository.findByEmail(email);
        Member otherMember = memberRepository.findByNickname(otherNickName);

        ChatRoom chatRoom = new ChatRoom(loginMember,otherMember);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    //고유 id를 통한 채팅방 조회
    public ChatRoom findByRoomId(long roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다.")
        );
    }
    //난주 만약 채팅방 이름 넣으면 이름으로 채팅방 조회 바꾸기
//    public ChatRoom findByRoomId(long roomID) {
//
//        return chatRoomRepository.findByRoomId(roomID);
//    }

    public void save(ChatRoom updatedRoom) {
        chatRoomRepository.save(updatedRoom);
    }

    public ResponseEntity<ChatRoomResponseDto> getAvailableRoom(String otherNickName, String email) {
        Member loginMember = memberRepository.findByEmail(email);
        Member otherMember = memberRepository.findByNickname(otherNickName);
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByFromMemberIdAndToMemberId(loginMember.getNickname(),otherNickName);

        if( chatRoom!= null)
            return ResponseEntity.status(200).body(ChatRoomResponseDto.builder()
                    .roomId(chatRoom.getRoomId())
                    .build());
        else
            chatRoom = createChatRoom(otherNickName,email);
            return ResponseEntity.status(200).body(ChatRoomResponseDto.builder()
                    .roomId(chatRoom.getRoomId())
                    .build());
    }

}