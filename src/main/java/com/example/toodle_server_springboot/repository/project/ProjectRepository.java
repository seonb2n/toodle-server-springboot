package com.example.toodle_server_springboot.repository.project;

import com.example.toodle_server_springboot.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
