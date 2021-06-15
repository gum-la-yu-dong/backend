package com.gumlayudong.gumlayudongbackend.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {
    private Long id;
    private String title;
    private String content;
    private String referenceUrl;

    public PostRequest(Long id, String title, String content, String referenceUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.referenceUrl = referenceUrl;
    }
}
