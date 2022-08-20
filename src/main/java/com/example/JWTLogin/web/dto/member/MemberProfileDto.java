package com.example.JWTLogin.web.dto.member;


import com.example.JWTLogin.domain.Member;
import lombok.*;

/**
 * 회원의 프로필을 조회시
 * 조회하는 프로필의 본인인지에 대한 여부, 팔로잉 상태
 * 회원의 정보, 게시글 수, 팔로워 수, 팔로잉 수를 보낸다.
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class MemberProfileDto {
    private boolean loginMember; //본인 계정인지 여부
    private boolean follow; // 팔로잉 상태
    private Member member;
    private int postCount;
    private int memberFollowerCount;
    private int memberFollowingCount;
}
