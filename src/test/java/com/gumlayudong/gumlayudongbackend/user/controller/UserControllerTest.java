package com.gumlayudong.gumlayudongbackend.user.controller;

import com.gumlayudong.gumlayudongbackend.exception.InvalidInputException;
import com.gumlayudong.gumlayudongbackend.user.application.UserService;
import com.gumlayudong.gumlayudongbackend.user.dto.UserRequest;
import com.gumlayudong.gumlayudongbackend.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
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

@DisplayName("유저 컨트롤러 테스트")
@WebFluxTest(controllers = UserController.class)
@AutoConfigureRestDocs
class UserControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    private UserRequest userRequest;

    @BeforeEach
    void init() {
        userRequest = new UserRequest("gump@naver.com", "나는 잘생긴 권영훈", "123", "나는 잘생김", "www.hello.com");
    }

    @DisplayName("회원가입 - 성공")
    @Test
    void signUpTest() {
        //given
        given(userService.save(any(UserRequest.class))).willReturn(new UserResponse(1L, null, null, null, null));

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_저장_요청(userRequest);

        //then
        사용자_생성됨(saveResponse);
    }

    @DisplayName("회원가입 - 실패")
    @Test
    void signUpFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).save(any(UserRequest.class));

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_저장_요청(userRequest);

        //then
        사용자_생성_실패됨(saveResponse);
    }

    private WebTestClient.ResponseSpec 사용자_저장_요청(UserRequest saveRequest) {
        return webTestClient.post().uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveRequest)
                .exchange();
    }

    private void 사용자_생성됨(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isCreated()
                .expectHeader().exists("Location")
                .expectBody().consumeWith(document("user-create"));
    }

    private void 사용자_생성_실패됨(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isBadRequest()
                .expectBody().consumeWith(document("user-create-fail"));
    }
}