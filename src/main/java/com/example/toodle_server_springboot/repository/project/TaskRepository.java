package com.example.toodle_server_springboot.repository.project;

import com.example.toodle_server_springboot.domain.project.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}
