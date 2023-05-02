package com.example.toodle_server_springboot.exception;

public class UserEmailSendFailException extends CustomException {

    public UserEmailSendFailException() {
        super(ErrorCode.USER_EMAIL_SEND_FAIL_EXCEPTION);
    }

}
