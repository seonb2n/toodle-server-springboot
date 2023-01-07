package com.example.toodle_server_springboot.dto;

import com.example.toodle_server_springboot.domain.user.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        String email,
        String password,
        String nickname,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto of(String email, String password, String nickname, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(email, password, nickname, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto of(String email, String password, String nickname) {
        return new UserAccountDto(email, password, nickname, null, null, null, null);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
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
