package com.gumlayudong.gumlayudongbackend.comment.domain;

import com.gumlayudong.gumlayudongbackend.common.domain.BaseEntity;
import com.gumlayudong.gumlayudongbackend.post.domain.Post;
import com.gumlayudong.gumlayudongbackend.tag.Tag;
import com.gumlayudong.gumlayudongbackend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    private Long id;
    private Post post;
    private User user;
    private Tag tag;
    private String content;

    public Comment(Post post, User user, Tag tag, String content) {
        this(null, post, user, tag, content);
    }

    public Comment(Long id, Post post, User user, Tag tag, String content) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.tag = tag;
        this.content = content;
    }
}
