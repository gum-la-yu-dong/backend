package com.gumlayudong.gumlayudongbackend.user.controller;

import com.gumlayudong.gumlayudongbackend.user.application.UserService;
import com.gumlayudong.gumlayudongbackend.user.dto.UserSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@DisplayName("유저 인수테스트")
@WebFluxTest
@AutoConfigureRestDocs
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("정상적으로 회원가입이 완료된다.")
    @Test
    void signUpTest() {
        //given
        String nickname = "하이";
        String password = "123";
        String introduction = "나는 잘생김";
        String githubUrl = "";

        //when
        UserSaveRequest saveRequest = new UserSaveRequest(nickname, password, introduction, githubUrl);
        given(userService.save(any(UserSaveRequest.class))).willReturn("하이");
        //then
        webTestClient.post().uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody().consumeWith(document("user-create"));
    }

    @DisplayName("정상적으로 회원가입이 완료된다. - 예외")
    @Test
    void signUpTestFailure() {
        //given
        String nickname = "바이0";
        String password = "123";
        String introduction = "나는 잘생김";
        String githubUrl = "";

        //when
        UserSaveRequest saveRequest = new UserSaveRequest(nickname, password, introduction, githubUrl);
        willThrow(IllegalArgumentException.class).given(userService).save(any(UserSaveRequest.class));

        //then
        webTestClient.post().uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().consumeWith(document("user-create-fail"));
    }
}