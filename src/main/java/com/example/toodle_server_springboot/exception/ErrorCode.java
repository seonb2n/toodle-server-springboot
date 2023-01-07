package com.example.toodle_server_springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INVALID_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 계정 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
