package com.example.toodle_server_springboot.dto.response;

import com.example.toodle_server_springboot.dto.UserAccountDto;

public record UserAccountResponse(
        String userEmail,
        String userPassword,
        String userNickName
) {
    public static UserAccountResponse from(UserAccountDto dto) {
        return new UserAccountResponse(
                dto.nickname(),
                dto.password(),
                dto.email()
        );
    }
}
