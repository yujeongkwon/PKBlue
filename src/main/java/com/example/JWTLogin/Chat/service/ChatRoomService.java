package com.example.JWTLogin.Chat.service;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatRoomResponseDto;
import com.example.JWTLogin.Chat.repository.ChatMessageRepository;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageService chatMessageService;

    // 현재 채팅방 조회
    public ArrayList<ChatRoom> findAllRooms(long  memberID) {
        return chatRoomRepository.findMyRooms(memberID);
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
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByFromMemberIdAndToMemberId(loginMember.getId(),otherMember.getId());

        if( chatRoom!= null) {
            //채팅방 드가면 읽음처리
            chatMessageService.readChat(otherMember.getId(),chatRoom.getRoomId());

            return ResponseEntity.status(200).body(ChatRoomResponseDto.builder()
                    .roomId(chatRoom.getRoomId())
                    .build());
        }else
            chatRoom = createChatRoom(otherNickName,email);
            return ResponseEntity.status(200).body(ChatRoomResponseDto.builder()
                    .roomId(chatRoom.getRoomId())
                    .build());
    }

}