package com.example.toodle_server_springboot.dto.response.project;

import com.example.toodle_server_springboot.dto.project.ProjectDto;

import java.util.List;

public record ProjectResponse(
        List<ProjectDto> projectDtoList
) {

    public static ProjectResponse of(List<ProjectDto> projectDtoList) {
        return new ProjectResponse(projectDtoList);
    }

}
