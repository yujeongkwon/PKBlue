package com.example.JWTLogin.web.dto.post;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class PostUpdateDto {

    private long id;
    private String tag;
    private String text;
}