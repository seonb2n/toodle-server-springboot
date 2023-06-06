package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.domain.log.UserRequestLog;
import com.example.toodle_server_springboot.repository.UserRequestLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("비즈니스 로직 - 로그")
@ExtendWith(MockitoExtension.class)
@Import({TestSecurityConfig.class})
@TestPropertySource(properties = { "spring.mail.username=sbkim@naver.com" })
class UserRequestLogServiceTest {

    @Mock private UserRequestLogRepository userRequestLogRepository;

    @InjectMocks private UserRequestLogService sut;

    @DisplayName("로그 저장을 요청하면, 성공적으로 저장된다.")
    @Test
    void givenCreateLogRequest_whenCreateLog_thenSaveLog() {
        //given
        UUID userIdUUID = UUID.randomUUID();
        UserRequestLog log = UserRequestLog.of(userIdUUID, "requestURL", HttpMethod.GET, HttpStatus.OK, "user-request", "user-response");
        given(userRequestLogRepository.save(log)).willReturn(log);

        //when
        sut.createLog(userIdUUID, "requestURL", HttpMethod.GET, HttpStatus.OK, "user-request", "user-response");

        //then
        verify(userRequestLogRepository).save(any(UserRequestLog.class));
    }

}