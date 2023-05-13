package com.example.toodle_server_springboot.dto.project;

import com.example.toodle_server_springboot.domain.project.Project;
import com.example.toodle_server_springboot.domain.project.task.Task;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record TaskDto(
    UUID taskId,
    UserAccountDto userAccountDto,
    String content,
    String importance,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Set<ActionDto> actionDtoSet
) {

    public static TaskDto of(UUID taskId, UserAccountDto userAccountDto, String content, String importance, LocalDateTime startAt, LocalDateTime endAt, Set<ActionDto> actionDtoSet) {
        return new TaskDto(taskId, userAccountDto, content, importance, startAt, endAt, actionDtoSet);
    }

    public static TaskDto from(Task task) {
        return TaskDto.of(
                task.getTaskId(),
                UserAccountDto.from(task.getUserAccount()),
                task.getContent(),
                task.getImportnace().toString(),
                task.getStartAt(),
                task.getEndAt(),
                task.getActionSet().stream().map(ActionDto::from).collect(Collectors.toSet())
        );
    }

    public Task toEntity(UserAccount userAccount, Project project) {
        return Task.of(
                userAccount,
                project,
                content,
                Task.IMPORTNACE.valueOf(importance)
        );
    }

}
