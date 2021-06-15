package com.gumlayudong.gumlayudongbackend.post.controller;

import com.gumlayudong.gumlayudongbackend.ControllerMockTest;
import com.gumlayudong.gumlayudongbackend.exception.InvalidInputException;
import com.gumlayudong.gumlayudongbackend.exception.NotFoundException;
import com.gumlayudong.gumlayudongbackend.post.dto.PostRequest;
import com.gumlayudong.gumlayudongbackend.post.dto.PostResponse;
import com.gumlayudong.gumlayudongbackend.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("게시글 컨트롤러 테스트")
@WebFluxTest(controllers = PostController.class)
public class PostControllerMockTest extends ControllerMockTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PostService postService;

    private PostRequest postRequest;
    private PostRequest errorPostRequest;

    @BeforeEach
    void init() {
        postRequest = new PostRequest(1L, "검프의 손맛", "소금 실패", "너무짜.com");
        errorPostRequest = new PostRequest(1L, "", "소금 실패", ".com");
    }

    @DisplayName("게시글 저장 - 성공")
    @Test
    void savePostTest() {
        //given
        given(postService.save(any(PostRequest.class))).willReturn(new PostResponse(1L, "검프의 손맛", "소금 실패", "너무짜.com"));

        //when
        WebTestClient.ResponseSpec saveResponse = 게시글_저장_요청(postRequest);

        //then
        게시글_생성됨(saveResponse);
    }

    @DisplayName("게시글 저장 - 실패")
    @Test
    void savePostFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(postService).save(any(PostRequest.class));

        //when
        WebTestClient.ResponseSpec saveResponse = 게시글_저장_요청(errorPostRequest);

        //then
        게시글_생성_실패됨(saveResponse);
    }

    @DisplayName("게시글 업데이트 - 성공")
    @Test
    void updatePost() {
        //given
        willDoNothing().given(postService).update(any(PostRequest.class));

        //when
        WebTestClient.ResponseSpec updateResponse = 게시글_수정_요청(postRequest);

        //then
        게시글_수정됨(updateResponse);
    }

    @DisplayName("게시글 업데이트 - 실패")
    @Test
    void updatePostFailure() {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(postService).update(any(PostRequest.class));

        //when
        WebTestClient.ResponseSpec updateResponse = 게시글_수정_요청(errorPostRequest);

        //then
        게시글_수정_실패됨(updateResponse);
    }

    @DisplayName("게시글 조회 - 성공")
    @Test
    void findPost() {
        //given
        given(postService.findById(any(Long.class))).willReturn(new PostResponse(1L, "검프의 손맛", "소금 실패", "너무짜" +
                ".com"));

        WebTestClient.ResponseSpec findResponse = 게시글_조회_요청();
        게시글_조회됨(findResponse);
    }

    @DisplayName("게시글 조회 - 실패")
    @Test
    void findPostFailure() {
        //given
        willThrow(new NotFoundException("잘못된 입력 예시")).given(postService).findById(any(Long.class));

        //when
        WebTestClient.ResponseSpec findResponse = 게시글_조회_요청();

        //then
        게시글_조회_실패됨(findResponse);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void deletePost() {
        //given
        willDoNothing().given(postService).delete(any(Long.class));

        WebTestClient.ResponseSpec deleteResponse = 게시글_삭제_요청();
        게시글_삭제됨(deleteResponse);
    }

    @DisplayName("게시글 삭제 - 실패")
    @Test
    void deletePostFailure() {
        //given
        willThrow(new NotFoundException("잘못된 입력 예시")).given(postService).delete(any(Long.class));

        //when
        WebTestClient.ResponseSpec deleteResponse = 게시글_삭제_요청();

        //then
        게시글_삭제_실패됨(deleteResponse);
    }

    private WebTestClient.BodyContentSpec 게시글_삭제_실패됨(WebTestClient.ResponseSpec deleteResponse) {
        return deleteResponse
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .consumeWith(toDocument("post-delete-fail"));
    }

    private WebTestClient.BodyContentSpec 게시글_삭제됨(WebTestClient.ResponseSpec deleteResponse) {
        return deleteResponse
                .expectStatus()
                .isNoContent()
                .expectBody()
                .consumeWith(toDocument("post-delete"));
    }

    private WebTestClient.BodyContentSpec 게시글_조회_실패됨(WebTestClient.ResponseSpec findResponse) {
        return findResponse
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .consumeWith(toDocument("post-find-fail"));
    }

    private WebTestClient.BodyContentSpec 게시글_조회됨(WebTestClient.ResponseSpec findResponse) {
        return findResponse
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(toDocument("post-find"));
    }

    private WebTestClient.ResponseSpec 게시글_조회_요청() {
        return webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/posts/{id}")
                                .build(1))
                .exchange();
    }

    private WebTestClient.ResponseSpec 게시글_삭제_요청() {
        return webTestClient.delete()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/posts/{id}")
                                .build(1))
                .exchange();
    }

    private WebTestClient.BodyContentSpec 게시글_수정됨(WebTestClient.ResponseSpec updateRequest) {
        return updateRequest
                .expectStatus()
                .isNoContent()
                .expectBody().consumeWith(toDocument("post-update"));
    }

    private WebTestClient.BodyContentSpec 게시글_수정_실패됨(WebTestClient.ResponseSpec updateRequest) {
        return updateRequest
                .expectStatus()
                .isBadRequest()
                .expectBody().consumeWith(toDocument("post-update-fail"));
    }

    private WebTestClient.ResponseSpec 게시글_수정_요청(PostRequest updateRequest) {
        return webTestClient.put().uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange();
    }


    private WebTestClient.ResponseSpec 게시글_저장_요청(PostRequest saveRequest) {
        return webTestClient.post().uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveRequest)
                .exchange();
    }

    private void 게시글_생성됨(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isCreated()
                .expectHeader().exists("Location")
                .expectBody().consumeWith(toDocument("post-create"));
    }

    private void 게시글_생성_실패됨(WebTestClient.ResponseSpec saveResponse) {
        saveResponse.expectStatus()
                .isBadRequest()
                .expectBody().consumeWith(toDocument("post-create-fail"));
    }
}