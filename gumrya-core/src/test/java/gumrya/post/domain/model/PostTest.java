package gumrya.post.domain.model;

import gumrya.exception.InvalidInputException;
import gumrya.post.domain.model.Post;
import gumrya.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Post 도메인 테스트")
class PostTest {

    private User user;
    private Post post;

    @BeforeEach
    public void setUp() {
        this.user = new User("gump@naer.com", "123", "닉네", "", "", "");
        this.post = new Post("검프", "포레스트", "포레스트.com", user);
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    public void create() {
        assertThatCode(() -> new Post("검프", "포레스트", "포레스트.com", user))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시글 생성 - 실패 - referenceUrl")
    @ParameterizedTest
    @NullSource
    public void createFailByInvalidReferenceUrl(String target) {
        assertThatThrownBy(() -> new Post("널", "안되요", target, user))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 생성 - 실패 - user")
    @ParameterizedTest
    @NullSource
    public void createFailByInvalidReferenceUrl(User target) {
        assertThatThrownBy(() -> new Post("널", "안되요", "하아", target))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 생성 - 실패 - title")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void createFailByInvalidTitle(String target) {
        assertThatThrownBy(() -> new Post(target, "검프", "포레스트.com", user))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 생성 - 실패 - content")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void createFailByInvalidContent(String target) {
        assertThatThrownBy(() -> new Post("검푸", target, "포레스트.com", user))
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

    @DisplayName("게시글 수정 - 실패 - referenceUrl")
    @ParameterizedTest
    @NullSource
    public void modifyFailByInvalidReferenceUrl(String target) {
        assertThatThrownBy(() -> post.modifyReferenceUrl(target)).isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 수정 - 실패 - title")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void modifyFailByInvalidTitle(String target) {
        assertThatThrownBy(() -> post.modifyTitle(target)).isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("게시글 수정 - 실패 - content")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    public void modifyFailByInvalidContent(String target) {
        assertThatThrownBy(() -> post.modifyContent(target)).isInstanceOf(InvalidInputException.class);
    }
}