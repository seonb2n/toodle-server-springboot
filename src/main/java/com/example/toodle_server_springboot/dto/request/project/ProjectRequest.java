package com.example.toodle_server_springboot.dto.request.project;

import java.util.List;
import java.util.UUID;

public record ProjectRequest(
        UUID uuid,
        String projectName,
        List<TaskRequest> taskRequestList
) {

    public static ProjectRequest of(UUID uuid, String projectName, List<TaskRequest> taskRequestList) {
        return new ProjectRequest(uuid, projectName, taskRequestList);
    }

}
