package com.gumlayudong.gumlayudongbackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 레포지토리 기능 테스트")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        //given
        User user = new User(
                "gump@naver.com", "123",
                "나는 잘생긴 권영훈", "나는 잘생김", "/user/img", "www.hello.com"
        );

        //when
        User saved = userRepository.save(user);
        User findUser = userRepository.findById(saved.getId()).get();

        //then
        assertThat(saved.getId()).isEqualTo(findUser.getId());
    }
}