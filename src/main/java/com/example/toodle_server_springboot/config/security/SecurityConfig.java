package com.example.toodle_server_springboot.config.security;

import com.example.toodle_server_springboot.dto.security.NaverOauth2Response;
import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import com.example.toodle_server_springboot.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Value("${react.ip}")
    String REACT_IP;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtRequestFilter jwtRequestFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
            ) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .antMatchers("/api/v1/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 간편 로그인 기능 사용을 위한 설정
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                )
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .csrf().disable()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(REACT_IP); // 허용할 특정 오리진
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * OAuth2 에서 사용될 userService
     * OAuth2 로부터 가져온 정보를 바탕으로 우리 db 에 사용자 등록을 한다.
     * @param userAccountService
     * @param passwordEncoder
     * @return
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            UserAccountService userAccountService,
            PasswordEncoder passwordEncoder
    ) {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            NaverOauth2Response naverOauth2Response = NaverOauth2Response.from(oAuth2User.getAttributes());
            String userEmail = "NAVER_" + naverOauth2Response.email();
            String dummyPassword = passwordEncoder.encode("{bcrypt}dummy");
            log.info(userEmail);
            return userAccountService.findUserAccountDto(userEmail)
                    .map(UserPrincipal::from)
                    .orElseGet(() ->
                            UserPrincipal.from(userAccountService.registerUser(
                                    userEmail,
                                    dummyPassword,
                                    naverOauth2Response.nickname()
                            )));
        };
    }
}

