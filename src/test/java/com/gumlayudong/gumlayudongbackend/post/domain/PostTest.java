package com.gumlayudong.gumlayudongbackend.post.domain;

import com.gumlayudong.gumlayudongbackend.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PostTest {

    private Post post;

    @BeforeEach
    public void setUp() {
        this.post = new Post("검프", "포레스트", "포레스트.com", new Post.User());
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    public void create() {
        assertThatCode(() -> new Post("검프", "포레스트", "포레스트.com", new Post.User()))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시글 생성 - referenceUrl - 실패")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = " ")
    public void createFailByInvalidReferenceUrl(String target) {
        assertThatThrownBy(() -> new Post("널", "안되요", target, new Post.User()))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 생성 - user - 실패")
    @ParameterizedTest
    @NullSource
    public void createFailByInvalidReferenceUrl(Post.User target) {
        assertThatThrownBy(() -> new Post("널", "안되요", "하아", target))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 생성 - title - 실패")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void createFailByInvalidTitle(String target) {
        assertThatThrownBy(() -> new Post(target, "검프", "포레스트.com", new Post.User()))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 생성 - content - 실패")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void createFailByInvalidContent(String target) {
        assertThatThrownBy(() -> new Post("검푸", target, "포레스트.com", new Post.User()))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    public void modify() {
        post.modifyTitle("라이언");
        post.modifyContent("ryan");
        post.modifyReferenceUrl("ryan.com");


        assertThat(post.getTitle()).isEqualTo("라이언");
        assertThat(post.getContent()).isEqualTo("ryan");
        assertThat(post.getReferenceUrl()).isEqualTo("ryan.com");
    }

    @DisplayName("게시글 수정 - referenceUrl - 실패")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = " ")
    public void modifyFailByInvalidReferenceUrl(String target) {
        assertThatThrownBy(() -> post.modifyReferenceUrl(target)).isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 수정 - title - 실패")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void modifyFailByInvalidTitle(String target) {
        assertThatThrownBy(() -> post.modifyTitle(target)).isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 수정 - content - 실패")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void modifyFailByInvalidContent(String target) {
        assertThatThrownBy(() -> post.modifyContent(target)).isInstanceOf(InvalidInputException.class);
    }
}