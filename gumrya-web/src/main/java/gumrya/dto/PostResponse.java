package gumrya.dto;

import gumrya.post.domain.model.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String referenceUrl;

    public PostResponse(Long id, String title, String content, String referenceUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.referenceUrl = referenceUrl;
    }

    public static PostResponse toDto(Post post) {
        return new PostResponse(
                post.getId(), post.getTitle(),
                post.getContent(), post.getReferenceUrl()
        );
    }
}
