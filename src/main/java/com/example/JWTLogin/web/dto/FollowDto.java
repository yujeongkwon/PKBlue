package com.example.JWTLogin.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigInteger;

/**
 * id, 닉네임, 프로필사진, 팔로우상태, 본인여부 를 담는다.
 */
@Builder
@AllArgsConstructor
@Getter
@Data
public class FollowDto {

    private long id;
    private String nickname;
    private String profileImgUrl;
    private int followState;
    private int loginUser;

}