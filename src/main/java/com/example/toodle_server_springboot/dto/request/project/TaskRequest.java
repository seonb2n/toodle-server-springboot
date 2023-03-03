package com.example.toodle_server_springboot.dto.request.project;

import java.util.List;
import java.util.UUID;

public record TaskRequest(
        UUID uuid,
        String content,
        List<ActionRequest> actionRequestList
) {

    public static TaskRequest of(UUID uuid, String content, List<ActionRequest> actionRequestList) {
        return new TaskRequest(uuid, content, actionRequestList);
    }

}
