package com.example.toodle_server_springboot.dto.postit;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record PostItDto(
        UUID postItId,
        PostItCategoryDto categoryDto,
        String content,
        String createdTime,
        boolean isDone
) {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static PostItDto of(PostItCategoryDto categoryDto, String content, boolean isDone) {
        return new PostItDto(null, categoryDto, content, LocalDateTime.now().format(DATE_TIME_FORMATTER), isDone);
    }

    public static PostItDto of(UUID postItId, PostItCategoryDto categoryDto, String content, LocalDateTime createdTime, boolean isDone) {
        return new PostItDto(postItId, categoryDto, content, createdTime.format(DATE_TIME_FORMATTER), isDone);
    }

    public static PostItDto from(PostIt postIt) {
        return PostItDto.of(
                postIt.getPostItClientId(),
                PostItCategoryDto.from(postIt.getPostICategory()),
                postIt.getContent(),
                postIt.getCreatedAt(),
                postIt.isDone()
        );
    }

    public PostIt toEntity(UserAccount userAccount) {
        return PostIt.of(
                content,
                userAccount
        );
    }

}
