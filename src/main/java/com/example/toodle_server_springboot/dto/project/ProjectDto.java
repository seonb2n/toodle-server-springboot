package com.example.toodle_server_springboot.dto.project;

import com.example.toodle_server_springboot.domain.project.task.Task;

import java.util.Set;
import java.util.UUID;

public record ProjectDto(
        UUID projectId,
        String projectName,
        Set<Task> taskSet
) {
}
