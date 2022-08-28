package com.example.JWTLogin.Chat.domain;

import com.example.JWTLogin.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long roomId;


    @JoinColumn(name = "from_member_id")
    @ManyToOne
    private Member fromMember;

    @JoinColumn(name = "to_member_id")
    @ManyToOne
    private Member toMember;


    @Builder
    public ChatRoom(Member fromMember, Member toMember){
        this.fromMember = fromMember;
        this.toMember = toMember;
    }
}