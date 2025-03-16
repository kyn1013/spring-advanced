package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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