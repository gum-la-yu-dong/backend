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
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@DisplayName("유저 컨트롤러 테스트")
@WebFluxTest
@AutoConfigureRestDocs
class UserControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    private UserRequest userRequest;
    private UserRequest userErrorRequest;
    private UserResponse userResponse;

    @BeforeEach
    void init() {
        userRequest = new UserRequest("gump@naver.com", "나는 잘생긴 권영훈", "123", "나는 잘생김", "/user/img", "www.hello.com");
        userErrorRequest = new UserRequest(null, null, null, null, null, null);
        userResponse = new UserResponse(1L, "나는 잘생긴 권영훈", "나는 잘생김", "/user/img", "www.hello.com");
    }

    @DisplayName("사용자 가입 - 성공")
    @Test
    void signUp() {
        //given
        given(userService.save(any(UserRequest.class))).willReturn(userResponse);

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_저장_요청(userRequest);

        //then
        사용자_생성됨(saveResponse);
    }

    @DisplayName("사용자 가입 - 실패")
    @Test
    void signUpFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).save(any(UserRequest.class));

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_저장_요청(userErrorRequest);

        //then
        사용자_생성_실패됨(saveResponse);
    }

    @DisplayName("사용자 정보 조회 - 성공")
    @Test
    void find() {
        //given
        given(userService.findUserByEmail(any(String.class))).willReturn(userResponse);

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_조회_요청("xxx@gmail.com");

        //then
        사용자_조회됨(saveResponse);
    }

    @DisplayName("사용자 정보 조회 - 실패")
    @Test
    void findFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).findUserByEmail(any(String.class));

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_조회_요청("@gmail.com");

        //then
        사용자_조회_요청_실패(saveResponse);
    }

    @DisplayName("사용자 업데이트 - 성공")
    @Test
    void userUpdate() {
        //given
        willDoNothing().given(userService).update(any(UserRequest.class));

        //when
        WebTestClient.ResponseSpec updateResponse = 사용자_업데이트_요청(userRequest);

        //then
        사용자_업데이트_성공함(updateResponse);
    }

    @DisplayName("사용자 업데이트 - 실패")
    @Test
    void userUpdateFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).update(any(UserRequest.class));

        //when
        WebTestClient.ResponseSpec updateResponse = 사용자_업데이트_요청(userErrorRequest);

        //then
        사용자_업데이트_실패함(updateResponse);
    }

    @DisplayName("사용자 삭제 - 성공")
    @Test
    void delete() {
        //given
        willDoNothing().given(userService).deleteByEmail(any(String.class));

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_삭제_요청("xxx@gmail.com");

        //then
        사용자_삭제됨(saveResponse);
    }

    @DisplayName("사용자 삭제 - 실패")
    @Test
    void deleteFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).deleteByEmail(any(String.class));

        //when
        WebTestClient.ResponseSpec saveResponse = 사용자_삭제_요청("@gmail.com");

        //then
        사용자_삭제_실패함(saveResponse);
    }


    private WebTestClient.ResponseSpec 사용자_업데이트_요청(UserRequest userRequest) {
        return webTestClient.put().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange();
    }


    private WebTestClient.ResponseSpec 사용자_저장_요청(UserRequest saveRequest) {
        return webTestClient.post().uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveRequest)
                .exchange();
    }

    private WebTestClient.ResponseSpec 사용자_조회_요청(String email) {
        return webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/users")
                                .queryParam("email", email)
                                .build())
                .exchange();
    }

    private WebTestClient.ResponseSpec 사용자_삭제_요청(String email) {
        return webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/users")
                        .queryParam("email", email)
                        .build())
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

    private void 사용자_업데이트_성공함(WebTestClient.ResponseSpec updateResponse) {
        updateResponse.expectStatus()
                .isNoContent()
                .expectBody().consumeWith(document("user-update"));
    }

    private void 사용자_조회됨(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isOk()
                .expectBody().consumeWith(document("user-detail"));
    }

    private void 사용자_조회_요청_실패(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isBadRequest()
                .expectBody()
                .consumeWith(document("user-detail-fail"));
    }

    private void 사용자_업데이트_실패함(WebTestClient.ResponseSpec updateResponse) {
        updateResponse.expectStatus()
                .isBadRequest()
                .expectBody().consumeWith(document("user-update-fail"));
    }

    private void 사용자_삭제됨(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isNoContent()
                .expectBody().consumeWith(document("user-delete"));
    }

    private void 사용자_삭제_실패함(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isBadRequest()
                .expectBody().consumeWith(document("user-delete-fail"));
    }
}