package com.example.toodle_server_springboot.dto.response.today;

import com.example.toodle_server_springboot.dto.response.project.ProjectResponse;

public record TodayResponse(
        ProjectResponse projectResponse,
        TodayPostItResponse todayPostItResponse
) {

    public static TodayResponse of(
            ProjectResponse projectResponse,
            TodayPostItResponse postItResponse
    ) {
        return new TodayResponse(projectResponse, postItResponse);

    }

}
