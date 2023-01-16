package com.example.toodle_server_springboot.dto.request;

import com.example.toodle_server_springboot.dto.PostItDto;

import java.time.LocalDateTime;

public record PostItRegisterRequest(
        String content,
        LocalDateTime endTime,
        boolean isDone
) {

    public PostItRegisterRequest of(String content, LocalDateTime endTime, boolean isDone) {
        return new PostItRegisterRequest(content, endTime, isDone);
    }

    public PostItDto toDto() {
        return PostItDto.of(content, endTime, isDone);
    }
}
