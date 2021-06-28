package gumrya.dto;

import gumrya.user.domain.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    public static UserResponse toDto(User user) {
        return new UserResponse(user.getId(), user.getNickname(), user.getIntroduction(), user.getProfileUrl(),
                user.getGithubUrl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(nickname, that.nickname) && Objects.equals(introduction, that.introduction) && Objects.equals(profileUrl, that.profileUrl) && Objects.equals(githubUrl, that.githubUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, introduction, profileUrl, githubUrl);
    }
}
