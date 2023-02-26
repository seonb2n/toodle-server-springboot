package com.example.toodle_server_springboot.dto.project;

import com.example.toodle_server_springboot.domain.project.Project;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProjectDto(
        UUID projectId,
        UserAccountDto userAccountDto,
        String projectName,
        Set<TaskDto> taskDtoSet
) {

    public static ProjectDto of(UUID projectId, UserAccountDto userAccountDto, String projectName, Set<TaskDto> taskDtoSet) {
        return new ProjectDto(projectId, userAccountDto, projectName, taskDtoSet);
    }

    public static ProjectDto from(Project project) {
        return ProjectDto.of(
                project.getProjectId(),
                UserAccountDto.from(project.getUserAccount()),
                project.getProjectName(),
                project.getTaskSet().stream().map(TaskDto::from).collect(Collectors.toSet())
        );
    }

    public Project toEntity(UserAccount userAccount) {
        return Project.of(
                userAccount,
                projectName
        );
    }



}
