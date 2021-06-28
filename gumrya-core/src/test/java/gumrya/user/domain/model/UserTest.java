package gumrya.user.domain.model;

import gumrya.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("User 도메인 테스트")
class UserTest {
    private User user;

    @BeforeEach
    public void init() {
        user = new User(
                1L, "gump@naver.com", "123",
                "나는 잘생긴 권영훈", "나는 잘생김", "/user/img", "www.hello.com"
        );
    }

    @DisplayName("사용자 생성 - 성공")
    @Test
    public void create() {
        //given
        //when
        //then
        assertThatCode(() -> new User(
                1L, "gump@naver.com", "123",
                "나는 잘생긴 권영훈", "나는 잘생김", "/user/img", "www.hello.com"))
                .doesNotThrowAnyException();
        assertThatCode(() -> new User(
                "gump@naver.com", "123", "나는 잘생긴 권영훈",
                "", "/user/img", "www.hello.com"))
                .doesNotThrowAnyException();

    }

    @DisplayName("사용자 생성 - 실패 - email")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    public void createEmailFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new User(
                text, "123", "나는 잘생긴 권영훈",
                "나는 잘생김", "/user/img", "www.hello.com"))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 생성 - 실패 - password")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    public void createPasswordFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new User(
                "gump@naver.com", null, "나는 잘생긴 권영훈",
                "나는 잘생김", "/user/img", "www.hello.com"))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 생성 - 실패 - nickname")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    public void createNicknameFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new User(
                "gump@naver.com", "123", text,
                "나는 잘생김", "/user/img", "www.hello.com"))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 생성 - 실패 - introduction")
    @ParameterizedTest
    @NullSource
    public void createIntroductionFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new User(
                "gump@naver.com", "123", "나는 잘생긴 권영훈",
                text, "/user/img", "www.hello.com"))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 생성 - 실패 - profileUrl")
    @ParameterizedTest
    @NullSource
    public void createProfileUrlFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new User(
                "gump@naver.com", "123", "나는 잘생긴 권영훈",
                "나는 잘생김", text, "www.hello.com"))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 생성 - 실패 - githubUrl")
    @ParameterizedTest
    @NullSource
    public void createGithubUrlFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new User(
                "gump@naver.com", "123", "나는 잘생긴 권영훈",
                "나는 잘생김", "/user/img", text))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 수정 - 성공")
    @Test
    public void modify() {
        //given
        String email = "xxx@naver.com";
        String password = "321";
        String nickname = "나는 생긴 권영훈";
        String introduction = "나는 생김";
        String profileUrl = "/user/img";
        String githubUrl = "www.hello.com";

        //when
        user.modifyEmail(email);
        user.modifyPassword(password);
        user.modifyNickname(nickname);
        user.modifyIntroduction(introduction);
        user.modifyProfileUrl(profileUrl);
        user.modifyGithubUrl(githubUrl);

        //then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getIntroduction()).isEqualTo(introduction);
        assertThat(user.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(user.getGithubUrl()).isEqualTo(githubUrl);
    }

    @DisplayName("사용자 수정 - 실패 - email")
    @ParameterizedTest
    @NullAndEmptySource
    public void modifyEmailFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> user.modifyEmail(text))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 수정 - 실패 - password")
    @ParameterizedTest
    @NullAndEmptySource
    public void modifyPasswordFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> user.modifyPassword(text))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 수정 - 실패 - nickName")
    @ParameterizedTest
    @NullAndEmptySource
    public void modifyNicknameFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> user.modifyNickname(text))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 수정 - 실패 - introduction")
    @ParameterizedTest
    @NullSource
    public void modifyIntroductionFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> user.modifyIntroduction(text))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 수정 - 실패 -profileUrl")
    @ParameterizedTest
    @NullSource
    public void modifyProfileUrlFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> user.modifyProfileUrl(text))
                .isInstanceOf(InvalidInputException.class);
    }

    @DisplayName("사용자 수정 - 실패 githubUrl")
    @ParameterizedTest
    @NullSource
    public void modifyGithubUrlFailure(String text) {
        //given
        //when
        //then
        assertThatThrownBy(() -> user.modifyGithubUrl(text))
                .isInstanceOf(InvalidInputException.class);
    }
}