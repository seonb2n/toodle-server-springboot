package com.example.toodle_server_springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INVALID_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 계정 입니다."),
    USER_EMAIL_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "해당 이메일로 가입된 사용자가 존재하지 않습니다."),
    USER_EMAIL_SEND_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 비번 변경 전송 메일이 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
