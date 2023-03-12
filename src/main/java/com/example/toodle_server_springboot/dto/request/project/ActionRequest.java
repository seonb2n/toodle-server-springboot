package com.example.toodle_server_springboot.dto.request.project;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.ActionDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActionRequest(
        UUID uuid,
        String content,
        LocalDateTime dueDate,
        boolean isDone
) {

    public static ActionRequest of(UUID uuid, String content, LocalDateTime dueDate, boolean isDone) {
        return new ActionRequest(uuid, content, dueDate, isDone);
    }

    public ActionDto toDto(UserAccountDto userAccountDto) {
        return ActionDto.of(uuid, userAccountDto, content, dueDate, isDone);
    }

}
