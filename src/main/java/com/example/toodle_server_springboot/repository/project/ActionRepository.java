package com.example.toodle_server_springboot.repository.project;

import com.example.toodle_server_springboot.domain.project.task.action.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
