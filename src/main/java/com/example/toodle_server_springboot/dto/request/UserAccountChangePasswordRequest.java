package com.example.toodle_server_springboot.dto.request;

public record UserAccountChangePasswordRequest(
    String userEmail
) {

    public static UserAccountChangePasswordRequest of(String userEmail) {
        return new UserAccountChangePasswordRequest(userEmail);
    }
}
