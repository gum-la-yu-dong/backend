package com.gumlayudong.gumlayudongbackend.post.domain;

import com.gumlayudong.gumlayudongbackend.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    private Long id;
    private Post post;
    private Tag tag;

    public PostTag(Post post, Tag tag) {
        this(null, post, tag);
    }

    public PostTag(Long id, Post post, Tag tag) {
        this.id = id;
        this.post = post;
        this.tag = tag;
    }
}
