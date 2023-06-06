package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.log.UserRequestLog;
import com.example.toodle_server_springboot.repository.UserRequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRequestLogService {

    private final UserRequestLogRepository userRequestLogRepository;

    /**
     * 생성된 로그를 서버로 저장하는 메서드
     */
    public void createLog(
            UUID userId,
            String requestURL,
            HttpMethod requestMethod,
            HttpStatus responseCode,
            String userRequestData,
            String userResponseData
    ) {
        userRequestLogRepository.save(UserRequestLog.of(userId, requestURL, requestMethod, responseCode, userRequestData, userResponseData));
    }

}
