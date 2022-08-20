package com.example.JWTLogin.web.dto.member;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * 회원가입 시
 * 이메일, 비밀번호, 닉네임을 기입한다.
 */
@Builder
@Getter
@AllArgsConstructor
@Setter
@Data
public class MemberSignupDto {

    @Size(min=2, max=100, message = "이메일은 2글자 이상, 100글자 이하로 작성해 주세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 이상의 비밀번호여야 합니다.")
    private String password;

    @Size(min=1, max=30, message = "이름은 1글자 이상, 30글자 이내로 작성해 주세요.")
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

}
