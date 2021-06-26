package gumrya.like.domain.model;

import gumrya.config.entity.BaseEntity;
import gumrya.post.domain.model.Post;
import gumrya.user.domain.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Like(Post post, User user) {
        this(null, post, user);
    }

    public Like(Long id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }
}

