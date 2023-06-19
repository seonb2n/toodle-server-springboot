package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.project.Project;
import com.example.toodle_server_springboot.domain.project.task.Task;
import com.example.toodle_server_springboot.domain.project.task.action.Action;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.ActionDto;
import com.example.toodle_server_springboot.dto.project.ProjectDto;
import com.example.toodle_server_springboot.dto.project.TaskDto;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import com.example.toodle_server_springboot.repository.project.ActionRepository;
import com.example.toodle_server_springboot.repository.project.ProjectRepository;
import com.example.toodle_server_springboot.repository.project.TaskRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ActionRepository actionRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ProjectDto> findAllProject(UserAccountDto userAccountDto) {
        var userAccount = userAccountRepository.findUserAccountByEmail(userAccountDto.email())
            .orElseThrow();
        var projectList = projectRepository.findAllByUserAccount_UserId(userAccount.getUserId());
        return projectList.stream().map(ProjectDto::from).toList();
    }

    /**
     * 새로운 프로젝트를 서버에 등록하는 메서드
     */
    @Transactional
    public ProjectDto registerProject(UserAccount userAccount, ProjectDto projectDto) {
        Project project = projectDto.toEntity(userAccount);
        var savedProject = projectRepository.save(project);
        projectDto.taskDtoSet()
            .forEach(taskDto -> registerTask(userAccount, savedProject, taskDto));
        return ProjectDto.from(savedProject);
    }

    /**
     * 기존에 존재하던 프로젝트를 수정하는 메서드
     */
    @Transactional
    public ProjectDto updateProject(UserAccount userAccount, ProjectDto projectDto) {
        Project originalProject = projectRepository.findById(projectDto.projectId()).orElseThrow();
        projectDto.taskDtoSet()
            .forEach(taskDto -> {
                Optional<Task> originalTaskOptional = taskRepository.findById(taskDto.taskId());
                if (originalTaskOptional.isEmpty()) {
                    registerTask(userAccount, originalProject, taskDto);
                } else {
                    Task savedTask = originalTaskOptional.get();
                    taskDto.actionDtoSet().forEach(actionDto -> {
                        Optional<Action> originalActionOptional = actionRepository.findById(
                            actionDto.actionId());
                        if (originalActionOptional.isEmpty()) {
                            registerAction(userAccount, savedTask, actionDto);
                        } else {
                            Action savedAction = originalActionOptional.get();
                            savedAction.update(actionDto.content(), actionDto.dueDate(),
                                actionDto.isDone());
                        }
                    });
                    savedTask.update(taskDto.content());
                }
            });
        originalProject.update(projectDto.projectName());
        return ProjectDto.from(originalProject);
    }

    /**
     * 기존에 존재하던 프로젝트를 삭제하는 메서드
     */
    @Transactional
    public void deleteProject(UserAccount userAccount, UUID projectId) {
        // 프로젝트의 소유주와 삭제 요청주가 동일한지 확인해야 함
        var projectOwner = projectRepository.findById(projectId).orElseThrow().getUserAccount();
        if (userAccount.equals(projectOwner)) {
            projectRepository.deleteProjectByProjectId(projectId);
        }
    }

    /**
     * 태스크를 새로 등록하는 메서드
     *
     * @param userAccount
     * @param project
     * @param taskDto
     * @return
     */
    public Task registerTask(UserAccount userAccount, Project project, TaskDto taskDto) {
        var task = Task.of(userAccount, project, taskDto.content(),
            Task.IMPORTNACE.valueOf(taskDto.importance()));
        taskRepository.save(task);
        taskDto.actionDtoSet()
            .forEach(actionDto -> registerAction(userAccount, task, actionDto));
        project.addTask(task);
        return task;
    }

    /**
     * 액션을 새로 등록하는 메서드
     *
     * @param userAccount
     * @param task
     * @param actionDto
     * @return
     */
    public Action registerAction(UserAccount userAccount, Task task, ActionDto actionDto) {
        Action action = Action.of(userAccount, task, actionDto.content(), actionDto.dueDate(),
            actionDto.isDone());
        actionRepository.save(action);
        task.addAction(action);
        return action;
    }
}
