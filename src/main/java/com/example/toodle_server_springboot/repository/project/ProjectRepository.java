package com.example.toodle_server_springboot.repository.project;

import com.example.toodle_server_springboot.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findAllByUserAccount_UserId(Long userId);

    void deleteProjectByProjectId(UUID projectId);

}
