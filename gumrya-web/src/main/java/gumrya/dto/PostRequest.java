package gumrya.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private Long userId;
    private Long postId;
    private String title;
    private String content;
    private String referenceUrl;

    public PostRequest(Long userId, Long postId, String title, String content, String referenceUrl) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.referenceUrl = referenceUrl;
    }
}
