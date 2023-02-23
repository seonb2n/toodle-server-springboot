package com.example.toodle_server_springboot.dto;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostItDto(
        UUID postItId,
        String content,
        LocalDateTime endTime,
        boolean isDone
) {

    public static PostItDto of(String content, LocalDateTime endTime, boolean isDone) {
        return new PostItDto(null, content, endTime, isDone);
    }

    public static PostItDto of(UUID postItId, String content, LocalDateTime endTime, boolean isDone) {
        return new PostItDto(postItId, content, endTime, isDone);
    }

    public static PostItDto from(PostIt postIt) {
        return PostItDto.of(
                postIt.getPostItId(),
                postIt.getContent(),
                postIt.getEndTime(),
                postIt.isDone()
        );
    }

    public PostIt toEntity(UserAccount userAccount) {
        return PostIt.of(
                content,
                userAccount,
                endTime
        );
    }

}
