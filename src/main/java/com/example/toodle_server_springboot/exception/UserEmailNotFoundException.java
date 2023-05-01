package com.example.toodle_server_springboot.exception;

public class UserEmailNotFoundException extends CustomException {

    public UserEmailNotFoundException() {
        super(ErrorCode.USER_EMAIL_NOT_FOUND_EXCEPTION);
    }

}
