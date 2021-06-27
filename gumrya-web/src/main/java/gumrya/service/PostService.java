package gumrya.service;

import gumrya.dto.PostRequest;
import gumrya.dto.PostResponse;
import gumrya.exception.NotFoundException;
import gumrya.post.domain.model.Post;
import gumrya.post.domain.repository.PostRepository;
import gumrya.user.domain.model.User;
import gumrya.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponse save(PostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 사용자입니다."));
        Post post = new Post(request.getTitle(), request.getContent(), request.getReferenceUrl(), user);

        Post savedPost = postRepository.save(post);
        return PostResponse.toDto(savedPost);
    }

    @Transactional
    public void update(PostRequest request) {
        Post post = findPost(request.getPostId());
        post.modifyTitle(request.getTitle());
        post.modifyContent(request.getContent());
        post.modifyReferenceUrl(request.getReferenceUrl());
    }

    public PostResponse findById(long id) {
        Post post = findPost(id);
        return PostResponse.toDto(post);
    }

    @Transactional
    public void delete(Long id) {
        if (!existsPost(id)) {
            throw new NotFoundException("찾을 수 없는 게시글입니다.");
        }
        postRepository.deleteById(id);
    }

    private boolean existsPost(Long id) {
        return postRepository.existsById(id);
    }

    private Post findPost(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 게시글입니다."));
    }
}
