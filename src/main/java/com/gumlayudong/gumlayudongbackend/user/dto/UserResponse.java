package com.gumlayudong.gumlayudongbackend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Long id;
    private String nickname;
    private String password;
    private String introduction;
    private String githubUrl;

    public UserResponse(Long id, String nickname, String password, String introduction, String githubUrl) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduction = introduction;
        this.githubUrl = githubUrl;
    }
}
