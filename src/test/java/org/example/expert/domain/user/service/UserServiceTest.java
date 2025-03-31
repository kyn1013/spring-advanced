package org.example.expert.domain.user.service;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    public void User를_Id로_조회할_수_있다() {
        // given
        String email = "yn1013@naver.com";
        long userId = 1L;
        User user = new User(email, "password", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when
        UserResponse userResponse = userService.getUser(userId);

        // then
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(userId);
        assertThat(userResponse.getEmail()).isEqualTo(email);
    }
    
    @Test
    public void 존재하지_않는_User_조회시에_InvalidRequestException을_던진다() {
        // given
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userService.getUser(userId));
        assertEquals("유저가 존재하지 않습니다.", exception.getErrorCode().getMessage());
    }

    @Test
    public void 비밀번호_변경시에_기존_비밀번호와_동일하면_InvalidRequestException을_던진다() {
        // given
        String email = "yn1013@naver.com";
        long userId = 1L;
        User user = new User(email, "oldPassword", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        String oldPassword = "oldPassword";
        String newPassword = "oldPassword";
        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, newPassword);

        given(passwordEncoder.matches(request.getNewPassword(), user.getPassword())).willReturn(true);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userService.changePassword(userId, request));
        assertEquals("이미 사용 중인 비밀번호 입니다.", exception.getErrorCode().getMessage());
    }

    @Test
    public void 비밀번호_변경시에_기존_비밀번호가_유효하지_않으면_InvalidRequestException을_던진다() {
        // given
        String email = "yn1013@naver.com";
        long userId = 1L;
        User user = new User(email, "password", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, newPassword);

        given(passwordEncoder.matches(request.getNewPassword(), user.getPassword())).willReturn(false);
        given(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).willReturn(false);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> userService.changePassword(userId, request));
        assertEquals("비밀번호가 잘못되었습니다.", exception.getErrorCode().getMessage());
    }

    @Test
    public void 비밀번호가_성공적으로_변경된다() {
        // given
        long userId = 1L;
        User user = new User("yn1013@naver.com", "oldPassword", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", userId);

        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, newPassword);

        // 서비스 내부에서 findById 실행
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // 첫 번째 검증 로직 통과하도록 설정
        given(passwordEncoder.matches(request.getNewPassword(), user.getPassword())).willReturn(false);
        // 두 번째 검증 로직 통과하도록 설정
        given(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).willReturn(true);
        // changePassword 안에 들어갈 값 설정
        given(passwordEncoder.encode(request.getNewPassword())).willReturn("encodedNewPassword");

        // when
        userService.changePassword(userId, request);

        // then
        assertEquals("encodedNewPassword", user.getPassword());
    }
}