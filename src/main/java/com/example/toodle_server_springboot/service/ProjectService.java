package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.project.Project;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.project.ProjectDto;
import com.example.toodle_server_springboot.repository.project.ActionRepository;
import com.example.toodle_server_springboot.repository.project.ProjectRepository;
import com.example.toodle_server_springboot.repository.project.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ActionRepository actionRepository;

    /**
     * 새로운 프로젝트를 서버에 등록하는 메서드
     */
    public ProjectDto registerProject(UserAccount userAccount, ProjectDto projectDto) {
        Project project = projectDto.toEntity(userAccount);
        var savedProject = projectRepository.save(project);
        return ProjectDto.from(savedProject);
    }

    /**
     * 기존에 존재하던 프로젝트를 수정하는 메서드
     */
    public void updateProject() {}

    /**
     * 기존에 존재하던 프로젝트를 삭제하는 메서드
     */
    public void deleteProject() {}
}
