package com.example.toodle_server_springboot.dto.request.project;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.TaskDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TaskRequest(
        String taskId,
        String content,
        String importance,
        LocalDateTime startAt,
        LocalDateTime endAt,
        List<ActionRequest> actionDtoSet
) {

    public static TaskRequest of(String uuid, String content, String importance, LocalDateTime startAt, LocalDateTime endAt, List<ActionRequest> actionRequestList) {
        return new TaskRequest(uuid, content, importance, startAt, endAt, actionRequestList);
    }

    public TaskDto toDto(UserAccountDto userAccountDto) {
        return TaskDto.of(
                UUID.fromString(taskId),
                userAccountDto,
                content,
                importance,
                startAt,
                endAt,
                actionDtoSet.stream()
                        .map(actionRequest -> actionRequest.toDto(userAccountDto)).collect(Collectors.toSet())
        );
    }

}
