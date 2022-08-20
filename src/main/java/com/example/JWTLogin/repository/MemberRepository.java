package com.example.JWTLogin.repository;

import com.example.JWTLogin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
}
