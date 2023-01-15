package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.config.security.JwtAuthenticationEntryPoint;
import com.example.toodle_server_springboot.config.security.SecurityConfig;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import({SecurityConfig.class, JwtAuthenticationEntryPoint.class})
public class TestSecurityConfig {

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * 인증된 사용자 권한 부여
     */
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.registerUser(anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of("sbkim@naver.com", "1q2w3e4r!!", "sbkim");
    }
}
