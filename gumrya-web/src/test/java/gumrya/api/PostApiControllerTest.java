package gumrya.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gumrya.dto.PostRequest;
import gumrya.dto.PostResponse;
import gumrya.exception.InvalidInputException;
import gumrya.exception.NotFoundException;
import gumrya.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostApiController.class)
class PostApiControllerTest extends ApiDocument {

    @MockBean
    private PostService postService;

    private PostRequest postRequest;
    private PostRequest errorPostRequest;

    @BeforeEach
    void init() {
        postRequest = new PostRequest(1L, 1L, "검프의 손맛", "소금 실패", "너무짜.com");
        errorPostRequest = new PostRequest(1L, 1L, "", "소금 실패", ".com");
    }

    @DisplayName("게시글 저장 - 성공")
    @Test
    void savePostTest() throws Exception {
        //given
        given(postService.save(any(PostRequest.class))).willReturn(new PostResponse(1L, "검프의 손맛", "소금 실패", "너무짜.com"));

        //when
        ResultActions saveResponse = 게시글_저장_요청(postRequest);

        //then
        게시글_생성됨(saveResponse, 1L);
    }

    @DisplayName("게시글 저장 - 실패")
    @Test
    void savePostFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(postService).save(any(PostRequest.class));

        //when
        ResultActions saveResponse = 게시글_저장_요청(errorPostRequest);

        //then
        게시글_생성_실패됨(saveResponse);
    }

    @DisplayName("게시글 업데이트 - 성공")
    @Test
    void updatePost() throws Exception {
        //given
        willDoNothing().given(postService).update(any(PostRequest.class));

        //when
        ResultActions updateResponse = 게시글_수정_요청(postRequest);

        //then
        게시글_수정됨(updateResponse);
    }

    @DisplayName("게시글 업데이트 - 실패")
    @Test
    void updatePostFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(postService).update(any(PostRequest.class));

        //when
        ResultActions updateResponse = 게시글_수정_요청(errorPostRequest);

        //then
        게시글_수정_실패됨(updateResponse);
    }

    @DisplayName("게시글 조회 - 성공")
    @Test
    void findPost() throws Exception {
        //given
        given(postService.findById(any(Long.class))).willReturn(new PostResponse(1L, "검프의 손맛", "소금 실패", "너무짜" +
                ".com"));

        ResultActions findResponse = 게시글_조회_요청(1L);
        게시글_조회됨(findResponse);
    }

    @DisplayName("게시글 조회 - 실패")
    @Test
    void findPostFailure() throws Exception {
        //given
        willThrow(new NotFoundException("잘못된 입력 예시")).given(postService).findById(any(Long.class));

        //when
        ResultActions findResponse = 게시글_조회_요청(1L);

        //then
        게시글_조회_실패됨(findResponse);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void deletePost() throws Exception {
        //given
        willDoNothing().given(postService).delete(any(Long.class));

        ResultActions deleteResponse = 게시글_삭제_요청(1L);
        게시글_삭제됨(deleteResponse);
    }

    @DisplayName("게시글 삭제 - 실패")
    @Test
    void deletePostFailure() throws Exception {
        //given
        willThrow(new NotFoundException("잘못된 입력 예시")).given(postService).delete(any(Long.class));

        //when
        ResultActions deleteResponse = 게시글_삭제_요청(1L);

        //then
        게시글_삭제_실패됨(deleteResponse);
    }

    private ResultActions 게시글_삭제_실패됨(ResultActions deleteResponse) throws Exception {
        return deleteResponse
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("post-delete-fail"));
    }

    private ResultActions 게시글_삭제됨(ResultActions deleteResponse) throws Exception {
        return deleteResponse
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-delete"));
    }

    private ResultActions 게시글_조회_실패됨(ResultActions findResponse) throws Exception {
        return findResponse
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("post-find-fail"));
    }

    private ResultActions 게시글_조회됨(ResultActions findResponse) throws Exception {
        return findResponse
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-find"));
    }

    private ResultActions 게시글_조회_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/posts/" + id));
    }

    private ResultActions 게시글_삭제_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/posts/" + id));
    }

    private ResultActions 게시글_수정됨(ResultActions updateRequest) throws Exception {
        return updateRequest
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-update"));
    }

    private ResultActions 게시글_수정_실패됨(ResultActions updateRequest) throws Exception {
        return updateRequest
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("post-update-fail"));
    }

    private ResultActions 게시글_수정_요청(PostRequest updateRequest) throws Exception {
        return mockMvc.perform(put("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequest)));
    }


    private ResultActions 게시글_저장_요청(PostRequest saveRequest) throws Exception {
        return mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveRequest)));
    }

    private void 게시글_생성됨(ResultActions saveResponse, Long id) throws Exception {
        saveResponse.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/posts/" + id))
                .andDo(print())
                .andDo(document("post-create"));
    }

    private void 게시글_생성_실패됨(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("post-create-fail"));
    }
}