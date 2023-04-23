package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.config.security.JwtTokenUtil;
import com.example.toodle_server_springboot.config.security.JwtUserDetailsService;
import com.example.toodle_server_springboot.dto.request.UserAccountAuthenticateRequest;
import com.example.toodle_server_springboot.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 인증")
@Import(TestSecurityConfig.class)
@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {

    private final MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean private JwtUserDetailsService jwtUserDetailsService;
    @MockBean private JwtTokenUtil jwtTokenUtil;
    @MockBean private AuthenticationManager authenticationManager;
    private UserAccountService userAccountService;

    UserAccountControllerTest(@Autowired MockMvc mvc, @Autowired UserAccountService userAccountService) {
        this.mvc= mvc;
        this.userAccountService = userAccountService;
    }

    @DisplayName("[view][POST] 로그인 api 컨트롤러 - 이메일, pw 없이 login api 호출시 400 에러가 발생한다.")
    @Test
    public void givenNothing_whenTryLogIn_thenReturn200() throws Exception {
        //given

        //when & then
        mvc.perform(post("/api/v1/users/login"))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[view][POST] 로그인 api 컨트롤러 - 이메일, pw 값이 제대로 있으면 200 과 토큰을 반환한다.")
    @Test
    public void givenLoginRequest_whenTryLogin_thenReturn200() throws Exception {
        //given
        UserAccountAuthenticateRequest request = new UserAccountAuthenticateRequest("sbkim@naver.com", "1q2w3e4r!!");
        given(jwtTokenUtil.generateToken(any())).willReturn("1234");
        given(authenticationManager.authenticate(any())).willReturn(null);
        //when & then
        mvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwtToken").isNotEmpty());
        then(jwtUserDetailsService).should().loadUserByUsername(anyString());
        then(jwtTokenUtil).should().generateToken(any());
    }

    @DisplayName("[view][GET] 회원가입 api 컨트롤러 - 이미 가입된 적이 있는 Email 인지 검증한다 - 해당 이메일이 존재")
    @Test
    public void givenUserEmail_whenVerifyUserEmail_thenReturn200() throws Exception {
        //given
        String userEmail = "sbkim@naver.com";
        given(userAccountService.checkEmail(userEmail)).willReturn(true);

        //when
        var response = mvc.perform(get("/api/v1/users/checkEmail")
                .param("userEmail", userEmail)
        )
                .andExpect(status().isOk())
                .andReturn();

        //then
        then(userAccountService).should().checkEmail(anyString());
        assertEquals("true", response.getResponse().getContentAsString());
    }

    @DisplayName("[VIEW][GET] 회원가입 api 컨트롤러 - 이미 가입된 적이 있는 Email 인지 검증한다 - 해당 이메일이 존재하지 않음")
    @Test
    public void given_when_then() throws Exception {
        //given
        String userEmail = "sbkim@naver.com";
        given(userAccountService.checkEmail(userEmail)).willReturn(false);

        //when
        var response = mvc.perform(get("/api/v1/users/checkEmail")
                        .param("userEmail", userEmail)
                )
                .andExpect(status().isOk())
                .andReturn();

        //then
        then(userAccountService).should().checkEmail(anyString());
        assertEquals("false", response.getResponse().getContentAsString());
    }

}
