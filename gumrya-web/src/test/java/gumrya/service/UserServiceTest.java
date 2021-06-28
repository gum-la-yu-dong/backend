package gumrya.service;

import gumrya.dto.UserRequest;
import gumrya.dto.UserResponse;
import gumrya.exception.DuplicateException;
import gumrya.exception.NotFoundException;
import gumrya.user.domain.model.User;
import gumrya.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
@DisplayName("User 비지니스 흐름 테스트")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserRequest userRequest;

    @BeforeEach
    void setup() {
        this.userRequest = new UserRequest(1L, "ryan@gmail.com", "12342n1k3l", "검프", "나는 검프다", "www.url.com",
                "github.com/younghoonkwon");
        this.user = new User(userRequest.getId(), userRequest.getEmail(), userRequest.getPassword(),
                userRequest.getNickname(),
                userRequest.getIntroduction(), userRequest.getProfileUrl(), userRequest.getGithubUrl());
    }

    @DisplayName("사용자 저장 - 성공")
    @Test
    void save() {
        //given
        given(userRepository.save(any(User.class))).willReturn(user);
        UserResponse userResponse = UserResponse.toDto(user);
        //when
        UserResponse saveUser = userService.save(userRequest);
        //then
        assertThat(saveUser).isEqualTo(userResponse);
    }

    @DisplayName("사용자 저장 - 실패")
    @Test
    void saveFail() {
        //given
        given(userRepository.existsByEmail(any(String.class))).willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> userService.save(userRequest))
                .isInstanceOf(DuplicateException.class);
    }

    @DisplayName("사용자 조회 - 성공")
    @Test
    void findByEmail() {
        //given
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.of(user));
        //when
        UserResponse findEmailResponse = userService.findByEmail(userRequest.getEmail());
        //then
        assertThat(findEmailResponse.getId()).isEqualTo(userRequest.getId());
    }

    @DisplayName("사용자 조회 - 실패")
    @Test
    void findByEmailFail() {
        //given
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userService.findByEmail("userRequest@email.com"))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("사용자 삭제 - 성공")
    @Test
    void deleteByEmail() {
        //given
        given(userRepository.existsByEmail(any(String.class))).willReturn(true);
        willDoNothing().given(userRepository).deleteByEmail(any(String.class));
        //when
        //then
        assertThatCode(() -> userService.deleteByEmail("ryan@gmail.com")).doesNotThrowAnyException();
    }

    @DisplayName("사용자 삭제 - 실패")
    @Test
    void deleteByEmailFail() {
        //given
        given(userRepository.existsByEmail(any(String.class))).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> userService.deleteByEmail("ryan@gmail.com"))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("사용자 수정 - 성공")
    @Test
    void updateByEmail() {
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        //when
        //then
        assertThatCode(() -> userService.update(userRequest)).doesNotThrowAnyException();
    }

    @DisplayName("사용자 수정 - 실패")
    @Test
    void updateByEmailFail() {
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userService.update(userRequest))
                .isInstanceOf(NotFoundException.class);
    }
}