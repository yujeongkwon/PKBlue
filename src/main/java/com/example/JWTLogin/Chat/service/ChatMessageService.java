package com.example.JWTLogin.Chat.service;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.Chat.dto.ChatMessageRequestDto;
import com.example.JWTLogin.Chat.repository.ChatMessageRepository;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;

    @Transactional
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else return "";
    }

    @Transactional
    public void message(ChatMessageRequestDto dto) {
        //멤버 아이디를 통해 닉네임을 찾은 후, sender 로 지정
        dto.setSender(memberRepository.findById(dto.getUserId()).get().getNickname());

        //메시지 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        dto.setCreatedAt(dateResult);

        ChatMessage message = ChatMessage.builder()
                .roomId(dto.getRoomId())
                .sender(dto.getSender())
                .message(dto.getMessage())
                .createdAt(dto.getCreatedAt())
                .chatRead(0)
                .build();


        chatMessageRepository.save(message);
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);

    }


    //안읽은 챗 개수
    @Transactional
    public int getAllUnreadChat(long otherId,long roomId){

        return chatMessageRepository.getAllUnreadChat(otherId, roomId);
    }


    //채팅방 드가면 읽음처리
    @Transactional
    public int readChat(long otherId,long roomId){
        return chatMessageRepository.readChat(otherId,roomId);
    }

    @Transactional
    public List<ChatMessage> getAllMessage(Long roomId) {

        return chatMessageRepository.findByRoomId(roomId);
    }


}