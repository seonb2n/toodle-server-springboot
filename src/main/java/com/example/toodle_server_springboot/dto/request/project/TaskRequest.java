package com.example.toodle_server_springboot.dto.request.project;

import java.util.List;
import java.util.UUID;

public record TaskRequest(
        UUID uuid,
        String content,
        List<ActionRequest> actionRequestList
) {
}
