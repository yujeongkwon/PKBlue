package com.example.JWTLogin.web.controller;

import com.example.JWTLogin.config.security.JwtTokenProvider;
import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.service.MemberService;
import com.example.JWTLogin.web.dto.member.MemberProfileDto;
import com.example.JWTLogin.web.dto.member.MemberSignupDto;
import com.example.JWTLogin.web.dto.member.MemberUpdateDto;
import com.example.JWTLogin.web.dto.member.NewbiMember;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;


    // 회원가입 + 이메일 닉네임 전달
    @PostMapping("/join")
    public NewbiMember join(@Valid @RequestBody MemberSignupDto signupDto) {
        memberService.save(signupDto);
        Member newbi = memberService.findByEmail(signupDto.getEmail());
        NewbiMember newbiMember = new NewbiMember(newbi);
        return newbiMember;
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user, RedirectAttributes redirectAttributes) {
        Member member = memberService.findByEmail(user.get("email"));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        //email
        String jwt = jwtTokenProvider.createToken(member.getEmail(), member.getRoles(),member.getUsername());
        redirectAttributes.addAttribute("jwt", jwt);
        return "redirect:/member/profile"; // 게시글이 보이는 페이지로 수정할 예정
    }

    //사용자 정보 수정 페이지로 이동 + 수정할 데이터폼 전달
    @GetMapping("/member/update")
    public MemberUpdateDto updateMemberRead() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        Member loginMember = memberService.findByEmail(email);
        MemberUpdateDto updateDto = new MemberUpdateDto(loginMember);
        return updateDto;
    }


    //사용자 정보 업데이트
    @PostMapping("/member/update")
    public String updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto, BindingResult bindingResult, @RequestParam("profileImgUrl") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
        Member loginMember = memberService.findByEmail(email);
        memberService.update(memberUpdateDto, multipartFile, email);
        redirectAttributes.addAttribute("id", loginMember.getId());
        return "redirect:/member/profile";
    }


    //사용자 프로필 화면으로 이동
    @GetMapping("/member/profile")
    public MemberProfileDto profile(@RequestParam long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  authentication.getName();
       MemberProfileDto memberProfileDto = memberService.getMemberProfileDto(id, email);
        return memberProfileDto;
    }

}