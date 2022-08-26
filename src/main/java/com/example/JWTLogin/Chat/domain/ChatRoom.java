package com.example.JWTLogin.Chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatRoom implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    private String roomName;
}