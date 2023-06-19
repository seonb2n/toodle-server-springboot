package com.example.toodle_server_springboot.dto.request.project;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.ProjectDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProjectRequest(
    UUID projectId,
    String projectName,
    String projectColor,
    List<TaskRequest> taskDtoSet
) {

    public static ProjectRequest of(UUID uuid, String projectName, String projectColor,
        List<TaskRequest> taskRequestList) {
        return new ProjectRequest(uuid, projectName, projectColor, taskRequestList);
    }

    public ProjectDto toDto(UserAccountDto userAccountDto) {
        return ProjectDto.of(
            projectId,
            userAccountDto,
            projectName,
            projectColor,
            taskDtoSet.stream()
                .map(taskRequest -> taskRequest.toDto(userAccountDto)).collect(Collectors.toSet())
        );
    }

}
