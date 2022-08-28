package com.example.JWTLogin.Chat.service;

import com.example.JWTLogin.Chat.domain.ChatMessage;
import com.example.JWTLogin.Chat.dto.ChatMessageRequestDto;
import com.example.JWTLogin.Chat.repository.ChatMessageRepository;
import com.example.JWTLogin.Chat.repository.ChatRoomRepository;
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
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;

    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else return "";
    }

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
                .build();

        redisTemplate.convertAndSend(channelTopic.getTopic(), message);

    }


    public List<ChatMessage> getAllMessage(Long roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }


}