package com.example.toodle_server_springboot.dto;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record PostItDto(
        UUID postItId,
        String content,
        String endTime,
        boolean isDone
) {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static PostItDto of(String content, LocalDateTime endTime, boolean isDone) {
        return new PostItDto(null, content, endTime.format(DATE_TIME_FORMATTER), isDone);
    }

    public static PostItDto of(UUID postItId, String content, LocalDateTime endTime, boolean isDone) {
        return new PostItDto(postItId, content, endTime.format(DATE_TIME_FORMATTER), isDone);
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
        LocalDate date = LocalDate.parse(endTime, DATE_TIME_FORMATTER);
        return PostIt.of(
                content,
                userAccount,
                LocalDateTime.of(date, LocalDateTime.now().toLocalTime())
        );
    }

}
