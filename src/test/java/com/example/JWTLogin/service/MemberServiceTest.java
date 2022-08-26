package com.example.JWTLogin.service;

import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.handler.CustomValidationException;
import com.example.JWTLogin.repository.FollowRepository;
import com.example.JWTLogin.repository.MemberRepository;
import com.example.JWTLogin.web.dto.member.MemberProfileDto;
import com.example.JWTLogin.web.dto.member.MemberSignupDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MemberServiceTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;



    @Autowired
    private MemberService memberService;

    @Test
    public void save_회원가입_성공() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("skaks1012316@naver.com")
                .password("audwns2021802V!")
                .nickname("명준캡짱")
                .build();

        //when
        memberService.save(memberSignupDto);
        Member saveMember = memberService.findByEmail(memberSignupDto.getEmail());

        //then
        org.assertj.core.api.Assertions.assertThat(saveMember).isNotNull();
        Assertions.assertThat(memberSignupDto.getEmail()).isEqualTo(saveMember.getEmail());
        Assertions.assertThat(memberSignupDto.getNickname()).isEqualTo(saveMember.getNickname());
    }

    @Test
    public void save_회원가입_실패() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("skaks1012316@naver.com")
                .password("audwns2021802V!")
                .nickname("짱")
                .build();

        memberService.save(memberSignupDto);
        //when
        assertThrows(CustomValidationException.class, () -> {
            memberService.save(memberSignupDto);
        });
    }

    @Test
    public void getMemberProfileDto_성공() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("skaks1012316@naver.com")
                .password("audwns2021802V!")
                .nickname("명준캡짱")
                .build();

        MemberSignupDto memberSignupDto2 = MemberSignupDto.builder()
                .email("skak6@naver.com")
                .password("audwns20802V!")
                .nickname("부경파랑새")
                .build();

        memberService.save(memberSignupDto);
        memberService.save(memberSignupDto2);
        Member loginMember = memberService.findByEmail(memberSignupDto.getEmail());
        Member wantoSeeMember = memberService.findByEmail(memberSignupDto2.getEmail());

        // when
        MemberProfileDto memberProfileDto = memberService.getMemberProfileDto(loginMember.getId(), wantoSeeMember.getEmail());

        //then
        Assertions.assertThat(memberProfileDto.getMemberFollowingCount()).isEqualTo(0);
        Assertions.assertThat(memberProfileDto.getMemberFollowerCount()).isEqualTo(0);
        Assertions.assertThat(memberProfileDto.isLoginMember()).isEqualTo(false);
        Assertions.assertThat(memberProfileDto.isFollow()).isEqualTo(false);
    }
}
