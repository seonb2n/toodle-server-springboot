package com.example.toodle_server_springboot.dto.request;

import com.example.toodle_server_springboot.dto.UserAccountDto;

public record UserAccountRegisterRequest(
        String userEmail,
        String userPassword,
        String userNickName
) {

    public static UserAccountRegisterRequest of(String userEmail, String userPassword, String userNickName) {
        return new UserAccountRegisterRequest(userEmail, userPassword, userNickName);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(userEmail, userPassword, userNickName);
    }
}
