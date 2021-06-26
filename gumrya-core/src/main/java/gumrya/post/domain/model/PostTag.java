package gumrya.post.domain.model;

import gumrya.config.entity.BaseEntity;
import gumrya.tag.domain.model.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
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

