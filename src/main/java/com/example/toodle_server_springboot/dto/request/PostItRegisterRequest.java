package com.example.toodle_server_springboot.dto.request;

import com.example.toodle_server_springboot.dto.PostItDto;

public record PostItRegisterRequest(
        String content,
        boolean isDone
) {

    public PostItRegisterRequest of(String content, boolean isDone) {
        return new PostItRegisterRequest(content, isDone);
    }

    public PostItDto toDto() {
        return PostItDto.of(content, isDone);
    }
}
