package com.example.toodle_server_springboot.repository.project;

import com.example.toodle_server_springboot.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    void deleteProjectByProjectId(UUID projectId);

}
