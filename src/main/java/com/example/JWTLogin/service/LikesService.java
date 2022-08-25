package com.example.JWTLogin.service;

import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.handler.CustomApiException;
import com.example.JWTLogin.repository.LikesRepository;
import com.example.JWTLogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void likes(long postId, String email) {
        Member loginMember = memberRepository.findByEmail(email);
        try {
            likesRepository.likes(postId, loginMember.getId());
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하였습니다.");
        }
    }

    @Transactional
    public void unLikes(long postId, String email) {
        Member loginMember = memberRepository.findByEmail(email);
        likesRepository.unLikes(postId, loginMember.getId());
    }
}