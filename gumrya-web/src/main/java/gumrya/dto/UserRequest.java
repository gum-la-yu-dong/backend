package gumrya.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String introduction;
    private String profileUrl;
    private String githubUrl;

    public UserRequest(Long id, String email, String password, String nickname, String introduction, String profileUrl,
                       String githubUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
        this.githubUrl = githubUrl;
    }

    public UserRequest(String email, String password, String nickname, String introduction, String profileUrl,
                       String githubUrl) {
        this(null, email, password, nickname, introduction, profileUrl, githubUrl);
    }
}
