package com.example.toodle_server_springboot.dto.security;

import java.util.Map;

public record NaverOauth2Response(
    String resultCode,
    String message,
    NaverAccount naverAccount
) {


    public static NaverOauth2Response from(Map<String, Object> attributes) {
        return new NaverOauth2Response(
            String.valueOf(attributes.get("resultcode")),
            String.valueOf(attributes.get("message")),
            NaverAccount.from((Map<String, Object>) attributes.get("response"))
        );
    }

    public record NaverAccount(
        String email,
        String nickName,
        String age,
        String gender,
        String name
    ) {

        public static NaverAccount from(Map<String, Object> attributes) {
            return new NaverAccount(
                String.valueOf(attributes.get("email")),
                String.valueOf(attributes.get("nickname")),
                String.valueOf(attributes.get("age")),
                String.valueOf(attributes.get("gender")),
                String.valueOf(attributes.get("name"))
            );
        }
    }

    public String email() {
        return this.naverAccount.email();
    }

    public String nickname() {
        return this.naverAccount.nickName();
    }

}
