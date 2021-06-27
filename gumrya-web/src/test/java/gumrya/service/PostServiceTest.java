package gumrya.service;

import gumrya.dto.PostRequest;
import gumrya.dto.PostResponse;
import gumrya.exception.NotFoundException;
import gumrya.post.domain.model.Post;
import gumrya.post.domain.repository.PostRepository;
import gumrya.user.domain.model.User;
import gumrya.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Post 비즈니스 흐름 테스트")
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private PostRequest postRequest;
    private User user;
    private Post post;

    @BeforeEach
    void init() {
        postRequest = new PostRequest(1L, 1L, "라이언의 손맛", "짜다", "");
        user = new User(1L, "검프", "123!@#", "검검프", "", "", "");
        post = new Post(postRequest.getPostId(), postRequest.getTitle(), postRequest.getContent(), postRequest.getReferenceUrl(), user);
    }

    @DisplayName("게시글 저장 - 성공")
    @Test
    void save() {
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(postRepository.save(any(Post.class))).willReturn(post);
        //when
        PostResponse response = postService.save(postRequest);
        //then
        assertThat(postRequest.getTitle()).isEqualTo(response.getTitle());
    }

    @DisplayName("게시글 저장 - 실패 - 찾을 수 없는 사용자")
    @Test
    void saveFail1() {
        //given
        willThrow(new NotFoundException("찾을 수 없는 사용자입니다.")).given(userRepository).findById(any(Long.class));
        //when
        //then
        assertThatThrownBy(() -> postService.save(postRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 저장 - 실패 - 제목이 null이거나 empty")
    @ParameterizedTest
    @NullAndEmptySource
    void saveFail2(String title) {
        //given
        PostRequest postRequest = new PostRequest(user.getId(), post.getId(), title, "본문", "링크");

        willThrow(new NotFoundException("찾을 수 없는 사용자입니다.")).given(userRepository).findById(any(Long.class));
        //when
        //then
        assertThatThrownBy(() -> postService.save(postRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 조회 - 성공")
    @Test
    void find() {
        //given
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));
        //when
        PostResponse response = postService.findById(postRequest.getPostId());
        //then
        assertThat(postRequest.getTitle()).isEqualTo(response.getTitle());
    }

    @DisplayName("게시글 조회 - 실패 - 찾을 수 없는 게시물")
    @Test
    void findFail() {
        //given
        willThrow(new NotFoundException("찾을 수 없는 게시글입니다.")).given(postRepository).findById(any(Long.class));
        //when
        //then
        assertThatThrownBy(() -> postService.findById(postRequest.getPostId()))
                .isInstanceOf(NotFoundException.class);
    }


    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));
        //when
        //then
        assertThatCode(() -> postService.update(postRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시글 수정 - 실패 - 찾을 수 없는 게시물")
    @Test
    void updateFail() {
        //given
        willThrow(new NotFoundException("찾을 수 없는 게시글입니다.")).given(postRepository).findById(any(Long.class));
        //when
        //then
        assertThatThrownBy(() -> postService.findById(postRequest.getPostId()))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete() {
        //given
        given(postRepository.existsById(any(Long.class))).willReturn(true);
        //when
        //then
        assertThatCode(() -> postService.delete(postRequest.getPostId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시글 삭제 - 실패 - 찾을 수 없는 게시물")
    @Test
    void deleteFail() {
        //given
        given(postRepository.existsById(any(Long.class))).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> postService.delete(postRequest.getPostId()))
                .isInstanceOf(NotFoundException.class);
    }
}