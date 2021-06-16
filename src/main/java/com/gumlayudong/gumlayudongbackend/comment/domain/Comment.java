package com.gumlayudong.gumlayudongbackend.comment.domain;

import com.gumlayudong.gumlayudongbackend.common.domain.BaseEntity;
import com.gumlayudong.gumlayudongbackend.post.domain.Post;
import com.gumlayudong.gumlayudongbackend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    public Comment(Post post, User user, String content) {
        this(null, post, user, content);
    }

    public Comment(Long id, Post post, User user, String content) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.content = content;
    }
}
