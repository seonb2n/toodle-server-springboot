package com.example.toodle_server_springboot.dto;

import com.example.toodle_server_springboot.domain.user.UserAccount;

public record UserAccountDto(
        String email,
        String password,
        String nickname
) {

    public static UserAccountDto of(String email, String password, String nickname) {
        return new UserAccountDto(email, password, nickname);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                email,
                nickname,
                password
        );
    }
}
