package gumrya.post.domain.model;

import gumrya.comment.domain.model.Comment;
import gumrya.config.entity.BaseEntity;
import gumrya.exception.InvalidInputException;
import gumrya.like.domain.model.Like;
import gumrya.user.domain.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<PostTag> postTags = new ArrayList<>();

    private String title;
    private String content;
    private String referenceUrl;

    public Post(String title, String content, String referenceUrl, User user) {
        this(null, title, content, referenceUrl, user);
    }

    public Post(Long id, String title, String content, String referenceUrl, User user) {
        validateTitle(title);
        validateContent(content);
        validateReferenceUrl(referenceUrl);
        validateUser(user);
        this.id = id;
        this.title = title;
        this.content = content;
        this.referenceUrl = referenceUrl;
        this.user = user;
    }

    public void modifyTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void modifyContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public void modifyReferenceUrl(String referenceUrl) {
        validateReferenceUrl(referenceUrl);
        this.referenceUrl = referenceUrl;
    }

    private void validateReferenceUrl(String referenceUrl) {
        validateNull(referenceUrl);
    }

    private void validateUser(User user) {
        validateNull(user);
    }

    private void validateTitle(String title) {
        validateNull(title);
        validateEmpty(title);
        validateBlank(title);
    }

    private void validateContent(String content) {
        validateNull(content);
        validateEmpty(content);
        validateBlank(content);
    }

    private void validateNull(Object text) {
        if (Objects.isNull(text)) {
            throw new InvalidInputException("null ?????? ??????????????? ?????? ??? ????????????.");
        }
    }

    private void validateEmpty(String text) {
        if (text.isEmpty()) {
            throw new InvalidInputException("??? ?????? ??????????????? ?????? ??? ????????????.");
        }
    }

    private void validateBlank(String text) {
        if (text.isBlank()) {
            throw new InvalidInputException("?????? ?????? ??????????????? ?????? ??? ????????????.");
        }
    }
}