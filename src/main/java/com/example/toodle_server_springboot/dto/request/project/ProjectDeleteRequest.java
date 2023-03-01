package com.example.toodle_server_springboot.dto.request.project;


import java.util.UUID;

public record ProjectDeleteRequest(
        UUID uuid
) {

    public ProjectDeleteRequest of(UUID uuid) {
        return new ProjectDeleteRequest(uuid);
    }

}
