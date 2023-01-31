package com.example.toodle_server_springboot.dto;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;

import java.time.LocalDateTime;

public record PostItDto(
        Long id,
        String content,
        LocalDateTime endTime,
        boolean isDone
) {

    public static PostItDto of(String content, LocalDateTime endTime, boolean isDone) {
        return new PostItDto(null, content, endTime, isDone);
    }

    public static PostItDto of(Long id, String content, LocalDateTime endTime, boolean isDone) {
        return new PostItDto(id, content, endTime, isDone);
    }

    public static PostItDto from(PostIt postIt) {
        return PostItDto.of(
                postIt.getId(),
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
