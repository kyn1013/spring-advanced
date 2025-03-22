package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 사용자가_잘_저장된다() {
        // given
        User user = new User("yn1013@naver.com", "password", UserRole.USER);

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(user).isSameAs(savedUser);
        assertThat(user.getEmail()).isSameAs(savedUser.getEmail());
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void 이메일로_사용자를_조회할_수_있다() {
        // given
        String email = "yn@abc.com";
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);

        // when
        User foundUser = userRepository.findByEmail(email).orElse(null);

        // then
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        assertEquals(UserRole.USER, foundUser.getUserRole());
    }

    @Test
    public void 이메일이_존재하는지_확인할_수_있다() {
        // given
        String email = "yn@abc.com";
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);

        // when
        Boolean emailExists = userRepository.existsByEmail(email);

        // then
        assertNotNull(emailExists);
        assertEquals(true, emailExists);
    }


}