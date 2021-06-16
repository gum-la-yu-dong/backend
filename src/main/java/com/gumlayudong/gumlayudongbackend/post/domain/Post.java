package com.gumlayudong.gumlayudongbackend.post.domain;

import com.gumlayudong.gumlayudongbackend.exception.InvalidInputException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Post {
    private Long id;
    private String title;
    private String content;
    private String referenceUrl;
    private User user;

    static class User{
        //needs to be deleted
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

    public Post(String title, String content, String referenceUrl, User user) {
        this(null, title, content, referenceUrl, user);
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
        validateBlank(referenceUrl);
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
            throw new InvalidInputException("null 값은 입력값으로 쓰실 수 없습니다.");
        }
    }

    private void validateEmpty(String text) {
        if (text.isEmpty()) {
            throw new InvalidInputException("빈 값은 입력값으로 쓰실 수 없습니다.");
        }
    }
    private void validateBlank(String text) {
        if (text.isBlank()) {
            throw new InvalidInputException("공백 값은 입력값으로 쓰실 수 없습니다.");
        }
    }
}
