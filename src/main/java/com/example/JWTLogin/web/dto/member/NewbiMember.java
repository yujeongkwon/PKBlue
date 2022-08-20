package com.example.JWTLogin.web.dto.member;

import com.example.JWTLogin.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@Data
public class NewbiMember {
    private String email;
    private String nickname;

    public NewbiMember(Member member){
        this.email = member.getEmail();
        this.nickname = member.getEmail();
    }
}
