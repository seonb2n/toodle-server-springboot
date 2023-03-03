package com.example.toodle_server_springboot.dto.request.project;

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

}
