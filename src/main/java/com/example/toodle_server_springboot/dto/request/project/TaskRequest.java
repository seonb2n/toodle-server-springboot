package com.example.toodle_server_springboot.dto.request.project;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.TaskDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TaskRequest(
        UUID uuid,
        String content,
        String importance,
        List<ActionRequest> actionRequestList
) {

    public static TaskRequest of(UUID uuid, String content, String importance, List<ActionRequest> actionRequestList) {
        return new TaskRequest(uuid, content, importance, actionRequestList);
    }

    public TaskDto toDto(UserAccountDto userAccountDto) {
        return TaskDto.of(
                uuid,
                userAccountDto,
                content,
                importance,
                actionRequestList.stream()
                        .map(actionRequest -> actionRequest.toDto(userAccountDto)).collect(Collectors.toSet())
        );
    }

}
