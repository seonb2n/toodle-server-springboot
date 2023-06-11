package com.example.toodle_server_springboot.config.security;

import com.example.toodle_server_springboot.service.RedisApiLimitCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * API 호출 제한을 검증하는 filter
 */
@Component
@RequiredArgsConstructor
public class APIRequestLimitFilter extends OncePerRequestFilter {

    private final RedisApiLimitCheckService redisApiLimitCheckService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (redisApiLimitCheckService.IPisInvalid(request.getRemoteAddr())) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429 Too Many Requests
            response.getWriter().write("API 호출 제한이 초과되었습니다."); // 에러 메시지
            return;
        }
        chain.doFilter(request, response);
    }
}
