package gumrya.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {

    private String email;
    private String password;
    private String nickname;
    private String introduction;
    private String profileUrl;
    private String githubUrl;

    public UserRequest(String email, String password, String nickname, String introduction, String profileUrl, String githubUrl) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.githubUrl = githubUrl;
    }
}
