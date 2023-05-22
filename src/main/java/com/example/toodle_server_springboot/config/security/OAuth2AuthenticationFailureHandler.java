package com.example.toodle_server_springboot.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        exception.printStackTrace();
        getExceptionMessage(exception);

        super.onAuthenticationFailure(request, response, exception);
    }

    /**
     * 예외에 대해서 메시지 출력
     * @param exception
     */
    private void getExceptionMessage(AuthenticationException exception) {
        logger.warn(exception.getMessage());
    }
}
