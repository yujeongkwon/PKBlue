package com.example.JWTLogin.repository;

import com.example.JWTLogin.Chat.domain.ChatRoom;
import com.example.JWTLogin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
    Member findByNickname(String nickname);

}
