package gumrya.service;

import gumrya.dto.UserRequest;
import gumrya.dto.UserResponse;
import gumrya.exception.DuplicateException;
import gumrya.exception.NotFoundException;
import gumrya.user.domain.model.User;
import gumrya.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse save(UserRequest userRequest) {
        if (existsByEmail(userRequest.getEmail())) {
            throw new DuplicateException("등록하려는 이메일의 사용자가 이미 존재합니다");
        }
        User user = new User(userRequest.getEmail(), userRequest.getPassword(), userRequest.getNickname(),
                userRequest.getIntroduction(), userRequest.getProfileUrl(), userRequest.getGithubUrl());
        User saveUser = userRepository.save(user);
        return new UserResponse(saveUser);
    }

    @Transactional
    public void update(UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new NotFoundException("수정하려는 이메일의 사용자가 없습니다."));
        user.modifyEmail(userRequest.getEmail());
        user.modifyPassword(userRequest.getPassword());
        user.modifyNickname(userRequest.getNickname());
        user.modifyIntroduction(userRequest.getIntroduction());
        user.modifyProfileUrl(userRequest.getProfileUrl());
        user.modifyGithubUrl(userRequest.getGithubUrl());
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("조회하려는 이메일의 사용자가 없습니다."));
        return new UserResponse(user);
    }

    @Transactional
    public void deleteByEmail(String email) {
        if (!existsByEmail(email)) {
            throw new NotFoundException("삭제하려는 이메일의 사용자가 없습니다");
        }
        userRepository.deleteByEmail(email);
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
