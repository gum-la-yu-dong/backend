package com.gumlayudong.gumlayudongbackend.user.domain;

import com.gumlayudong.gumlayudongbackend.common.domain.BaseEntity;
import com.gumlayudong.gumlayudongbackend.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String introduction;
    private String profileUrl;
    private String githubUrl;

    public User(String email, String password, String nickname, String introduction, String profileUrl, String githubUrl) {
        this(null, email, password, nickname, introduction, profileUrl, githubUrl);
    }

    public User(Long id, String email, String password, String nickname, String introduction, String profileUrl, String githubUrl) {
        validateEmail(email);
        validatePassword(password);
        validateNickname(nickname);
        validateIntroduction(introduction);
        validateProfileUrl(profileUrl);
        validateGithubUrl(githubUrl);
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.githubUrl = githubUrl;
    }

    public void modifyEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void modifyPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void modifyNickname(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    public void modifyIntroduction(String introduction) {
        validateIntroduction(introduction);
        this.introduction = introduction;
    }

    public void modifyProfileUrl(String profileUrl) {
        validateProfileUrl(profileUrl);
        this.profileUrl = profileUrl;
    }

    public void modifyGithubUrl(String githubUrl) {
        validateGithubUrl(githubUrl);
        this.githubUrl = githubUrl;
    }

    private void validateEmail(String email) {
        validateNull(email);
        validateEmpty(email);
        validateBlank(email);
    }

    private void validatePassword(String password) {
        validateNull(password);
        validateEmpty(password);
        validateBlank(password);
    }

    private void validateIntroduction(String introduction) {
        validateNull(introduction);
        validateBlank(introduction);
    }

    private void validateProfileUrl(String profileUrl) {
        validateNull(profileUrl);
        validateBlank(profileUrl);
    }

    private void validateGithubUrl(String githubUrl) {
        validateNull(githubUrl);
        validateBlank(githubUrl);
    }

    private void validateNickname(String nickname) {
        validateNull(nickname);
        validateEmpty(nickname);
        validateBlank(nickname);
    }

    private void validateNull(String text) {
        if (Objects.isNull(text)) {
            throw new InvalidInputException("필드 값을 입력해주세요 입력 값: null");
        }
    }

    private void validateEmpty(String text) {
        if (text.isEmpty()) {
            throw new InvalidInputException("필드 값을 입력해주세요 입력 값: " + text);
        }
    }

    private void validateBlank(String text) {
        if (text.isBlank()) {
            throw new InvalidInputException("필드 값을 입력해주세요 입력 값: " + text);
        }
    }
}
