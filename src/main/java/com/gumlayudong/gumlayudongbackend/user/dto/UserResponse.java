package com.gumlayudong.gumlayudongbackend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Long id;
    private String nickname;
    private String introduction;
    private String profileUrl;
    private String githubUrl;

    public UserResponse(Long id, String nickname, String introduction, String profileUrl, String githubUrl) {
        this.id = id;
        this.nickname = nickname;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.githubUrl = githubUrl;
    }
}
