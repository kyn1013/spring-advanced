package org.example.expert.domain.user.controller;

import org.example.expert.domain.common.errorcode.ErrorCode;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void user_단건_조회() throws Exception {
        // given
        long userId = 1L;
        String email = "yn1013@naver.com";

        BDDMockito.given(userService.getUser(userId)).willReturn(new UserResponse(userId, email));

        // when & then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
    }

    @Test
    public void user_단건_조회_시_user가_존재하지_않아_예외가_발생한다() throws Exception {
        // given
        long userId = 1L;

        // when
        Mockito.when(userService.getUser(userId))
                .thenThrow(new InvalidRequestException(ErrorCode.USER_NOT_FOUND));

        // then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value())) //숫자
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name())) //문자
                .andExpect(jsonPath("$.message").value("유저가 존재하지 않습니다."));
    }
}