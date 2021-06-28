package gumrya.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gumrya.dto.UserRequest;
import gumrya.dto.UserResponse;
import gumrya.exception.InvalidInputException;
import gumrya.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("유저 컨트롤러 테스트")
@WebMvcTest(controllers = UserApiController.class)
class UserApiControllerTest extends ApiDocument {

    @MockBean
    protected UserService userService;

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
    void signUp() throws Exception {
        //given
        given(userService.save(any(UserRequest.class))).willReturn(userResponse);

        //when
        ResultActions saveResponse = 사용자_저장_요청(userRequest);

        //then
        사용자_생성됨(saveResponse);
    }

    @DisplayName("사용자 가입 - 실패")
    @Test
    void signUpFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).save(any(UserRequest.class));

        //when
        ResultActions saveResponse = 사용자_저장_요청(userErrorRequest);

        //then
        사용자_생성_실패됨(saveResponse);
    }

    @DisplayName("사용자 정보 조회 - 성공")
    @Test
    void find() throws Exception {
        //given
        given(userService.findByEmail(any(String.class))).willReturn(userResponse);

        //when
        ResultActions saveResponse = 사용자_조회_요청("xxx@gmail.com");

        //then
        사용자_조회됨(saveResponse);
    }

    @DisplayName("사용자 정보 조회 - 실패")
    @Test
    void findFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).findByEmail(any(String.class));

        //when
        ResultActions saveResponse = 사용자_조회_요청("@gmail.com");

        //then
        사용자_조회_요청_실패(saveResponse);
    }

    @DisplayName("사용자 업데이트 - 성공")
    @Test
    void userUpdate() throws Exception {
        //given
        willDoNothing().given(userService).update(any(UserRequest.class));

        //when
        ResultActions updateResponse = 사용자_업데이트_요청(userRequest);

        //then
        사용자_업데이트_성공함(updateResponse);
    }

    @DisplayName("사용자 업데이트 - 실패")
    @Test
    void userUpdateFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).update(any(UserRequest.class));

        //when
        ResultActions updateResponse = 사용자_업데이트_요청(userErrorRequest);

        //then
        사용자_업데이트_실패함(updateResponse);
    }

    @DisplayName("사용자 삭제 - 성공")
    @Test
    void userDelete() throws Exception {
        //given
        willDoNothing().given(userService).deleteByEmail(any(String.class));

        //when
        ResultActions saveResponse = 사용자_삭제_요청("xxx@gmail.com");

        //then
        사용자_삭제됨(saveResponse);
    }

    @DisplayName("사용자 삭제 - 실패")
    @Test
    void deleteFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("잘못된 입력 예시")).given(userService).deleteByEmail(any(String.class));

        //when
        ResultActions saveResponse = 사용자_삭제_요청("@gmail.com");

        //then
        사용자_삭제_실패함(saveResponse);
    }


    private ResultActions 사용자_업데이트_요청(UserRequest userRequest) throws Exception {
        return mockMvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userRequest)));
    }


    private ResultActions 사용자_저장_요청(UserRequest saveRequest) throws Exception {
        return mockMvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(saveRequest))
        );
    }

    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalStateException("직렬화 오류");
        }
    }

    private ResultActions 사용자_조회_요청(String email) throws Exception {
        return mockMvc.perform(get("/api/users")
                .queryParam("email", email));
    }

    private ResultActions 사용자_삭제_요청(String email) throws Exception {
        return mockMvc.perform(delete("/api/users")
                .queryParam("email", email));
    }

    private void 사용자_생성됨(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/1"))
                .andDo(print())
                .andDo(toDocument("user-create"));
    }

    private void 사용자_생성_실패됨(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-create-fail"));
    }

    private void 사용자_업데이트_성공함(ResultActions updateResponse) throws Exception {
        updateResponse.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-update"));
    }

    private void 사용자_조회됨(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("user-detail"));
    }

    private void 사용자_조회_요청_실패(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-detail-fail"));
    }

    private void 사용자_업데이트_실패함(ResultActions updateResponse) throws Exception {
        updateResponse.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-update-fail"));
    }

    private void 사용자_삭제됨(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-delete"));
    }

    private void 사용자_삭제_실패함(ResultActions saveResponse) throws Exception {
        saveResponse.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-delete-fail"));
    }
}