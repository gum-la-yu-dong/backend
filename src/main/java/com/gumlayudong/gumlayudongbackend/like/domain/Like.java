package com.gumlayudong.gumlayudongbackend.like.domain;

import com.gumlayudong.gumlayudongbackend.post.domain.Post;
import com.gumlayudong.gumlayudongbackend.tag.Tag;
import com.gumlayudong.gumlayudongbackend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    private Long id;
    private Post post;
    private User user;
    private Tag tag;

    public Like(Post post, User user, Tag tag) {
        this(null, post, user, tag);
    }

    public Like(Long id, Post post, User user, Tag tag) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.tag = tag;
    }
}
