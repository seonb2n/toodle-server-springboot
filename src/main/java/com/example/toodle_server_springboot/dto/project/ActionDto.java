package com.example.toodle_server_springboot.dto.project;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActionDto(
        UUID actionId,
        String content,
        LocalDateTime dueDate,
        boolean isDone
) {

}
