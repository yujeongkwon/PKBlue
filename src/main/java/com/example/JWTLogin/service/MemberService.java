package com.example.JWTLogin.service;

import com.example.JWTLogin.domain.Member;
import com.example.JWTLogin.handler.CustomValidationException;
import com.example.JWTLogin.repository.FollowRepository;
import com.example.JWTLogin.repository.MemberRepository;
import com.example.JWTLogin.web.dto.member.MemberProfileDto;
import com.example.JWTLogin.web.dto.member.MemberSignupDto;
import com.example.JWTLogin.web.dto.member.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;
    public final FollowRepository followRepository;

    /** 회원가입 (이메일 , 닉네임 중복 확인)
     *  최초 회원 가입시, 자기소개와 프로필 사진은 없다 -> 프로필 수정을 통해 기입
     */
    @Transactional
    public void save(MemberSignupDto signupDto){
        if(memberRepository.findByEmail(signupDto.getEmail()) != null) {
            throw new CustomValidationException("이미 존재하는 이메일입니다.");
        }

        try{
            memberRepository.findByNickname(signupDto.getNickname());
        } catch(Exception e){
            throw new CustomValidationException("이미 존재하는 닉네임입니다.");
        }
        Member saveMember = Member.builder()
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .nickname(signupDto.getNickname())
                .introduce(null)
                .profileImgUrl(null)
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build();
        memberRepository.save(saveMember);
    }


    /**회원정보 수정 (비밀번호, 닉네임, 자기소개, 프로필사진)
     * email 값을 통해 Member를 찾아준 뒤,
     * MemberUpdateDto로 받아들인 정보로 수정한다.
     */
    @Value("${profileImg.path}")
    private String uploadFolder;
    @Transactional  // 이메일
    public void update(MemberUpdateDto memberUpdateDto, MultipartFile multipartFile, String email) {
        Member loginMember = memberRepository.findByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!multipartFile.isEmpty()) { //파일이 업로드 되었는지 확인
            String imageFileName = loginMember.getId() + "_" + multipartFile.getOriginalFilename();
            Path imageFilePath = Paths.get(uploadFolder + imageFileName);
            try {
                if (loginMember.getProfileImgUrl() != null) { // 이미 프로필 사진이 있을경우
                    File file = new File(uploadFolder + loginMember.getProfileImgUrl());
                    file.delete(); // 원래파일 삭제
                }
                Files.write(imageFilePath, multipartFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            loginMember.updateProfileImgUrl(imageFileName);
        }

        loginMember.update(
                encoder.encode(memberUpdateDto.getPassword()),
                memberUpdateDto.getNickname(),
                memberUpdateDto.getIntroduce(),
                memberUpdateDto.getProfileImgUrl() // 필요없음
        );

    }

    // 회원 프로필 조회
    @Transactional
    public MemberProfileDto getMemberProfileDto(long profileId, String email) {
        MemberProfileDto memberProfileDto = new MemberProfileDto();

        Member nowMember = memberRepository.findById(profileId).orElseThrow(() -> { return new CustomValidationException("찾을 수 없는 user입니다.");});
        memberProfileDto.setMember(nowMember);
        //게시물 수
        //MemberProfileDto.setPostCount(nowMember.getPostList().size());

        // loginEmail 활용하여 currentId가 로그인된 사용자 인지 확인
        Member loginMember = findByEmail(email);
        memberProfileDto.setLoginMember(loginMember.getId() == nowMember.getId());

        // 현재 loginEmail를 가진 member가 보려는 nowMember를 구독 했는지 확인
        memberProfileDto.setFollow(followRepository.findFollowByFromMemberIdAndToMemberId(loginMember.getId(), nowMember.getId()) != null);

        //nowMember의 팔로워, 팔로잉 수를 확인한다.
        memberProfileDto.setMemberFollowerCount(followRepository.findFollowerCountById(profileId));
        memberProfileDto.setMemberFollowingCount(followRepository.findFollowingCountById(profileId));

        //좋아요 수 확인
        //nowMember.getPostList().forEach(post -> {
        //    post.updateLikesCount(post.getLikesList().size());
        //});

        return memberProfileDto;
    }

    public Member findByEmail(String email){
        Member findMember = memberRepository.findByEmail(email);
        return findMember;
    }
}
