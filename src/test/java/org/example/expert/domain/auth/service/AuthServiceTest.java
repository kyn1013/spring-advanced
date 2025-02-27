package org.example.expert.domain.auth.service;

import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.example.expert.domain.user.enums.UserRole.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void 회원_등록_중_이미_존재하는_이메일로_가입하는_경우_에러가_발생한다() {
        // given
        User user = new User("1@naver.com", "0000", USER);
        given(userRepository.existsByEmail("1@naver.com")).willReturn(true);
        SignupRequest signupRequest = new SignupRequest("1@naver.com", "0000", "USER");

        //when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                authService.signup(signupRequest)
        );

        //then
        assertEquals("이미 사용 중인 이메일입니다.", exception.getErrorCode().getMessage());
    }

    @Test
    void 로그인_중_존재하지_않는_이메일로_로그인하는_경우_에러가_발생한다() {
        // given
        given(userRepository.findByEmail("1@naver.com")).willReturn(Optional.empty());
        SigninRequest signinRequest = new SigninRequest("1@naver.com", "0000");

        //when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                authService.signin(signinRequest)
        );

        //then
        assertEquals("해당 이메일로 등록된 유저가 없습니다.", exception.getErrorCode().getMessage());
    }


}