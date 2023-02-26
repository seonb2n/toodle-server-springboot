package com.example.toodle_server_springboot.dto.project;

import com.example.toodle_server_springboot.domain.project.task.Task;
import com.example.toodle_server_springboot.domain.project.task.action.Action;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActionDto(
        UUID actionId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime dueDate,
        boolean isDone
) {

    public static ActionDto of(UUID actionId, UserAccountDto userAccountDto, String content, LocalDateTime dueDate, boolean isDone) {
        return new ActionDto(actionId, userAccountDto, content, dueDate, isDone);
    }

    public static ActionDto of(UserAccountDto userAccountDto, String content, LocalDateTime dueDate, boolean isDone) {
        return new ActionDto(null, userAccountDto, content, dueDate, isDone);
    }

    public static ActionDto from(Action action) {
        return ActionDto.of(
                action.getActionId(),
                UserAccountDto.from(action.getUserAccount()),
                action.getContent(),
                action.getDueDate(),
                action.isDone()
        );
    }

    public Action toEntity(UserAccount userAccount, Task task) {
        return Action.of(userAccount, task, content, dueDate, isDone);
    }

}
