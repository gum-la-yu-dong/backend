package gumrya.user.domain.model;

import gumrya.config.entity.BaseEntity;
import gumrya.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
    }

    private void validateProfileUrl(String profileUrl) {
        validateNull(profileUrl);
    }

    private void validateGithubUrl(String githubUrl) {
        validateNull(githubUrl);
    }

    private void validateNickname(String nickname) {
        validateNull(nickname);
        validateEmpty(nickname);
        validateBlank(nickname);
    }

    private void validateNull(String text) {
        if (Objects.isNull(text)) {
            throw new InvalidInputException("?????? ?????? ?????????????????? ?????? ???: null");
        }
    }

    private void validateEmpty(String text) {
        if (text.isEmpty()) {
            throw new InvalidInputException("?????? ?????? ?????????????????? ?????? ???: " + text);
        }
    }

    private void validateBlank(String text) {
        if (text.isBlank()) {
            throw new InvalidInputException("?????? ?????? ?????????????????? ?????? ???: " + text);
        }
    }
}

