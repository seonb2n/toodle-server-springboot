package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.config.security.*;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.service.RedisApiLimitCheckService;
import com.example.toodle_server_springboot.service.UserAccountService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import({SecurityConfig.class, JwtAuthenticationEntryPoint.class, JwtRequestFilter.class, JwtTokenUtil.class, JwtUserDetailsService.class, OAuth2AuthenticationFailureHandler.class, OAuth2AuthenticationSuccessHandler.class})
public class TestSecurityConfig {

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private RedisApiLimitCheckService redisApiLimitCheckService;

    /**
     * 인증된 사용자 권한 부여
     */
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountService.findUserAccountDto(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.registerUser(anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
        given(userAccountService.findUserAccount(anyString())).willReturn(createUserAccountDto().toEntity());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of("sbkim@naver.com", "1q2w3e4r!!", "sbkim");
    }
}
