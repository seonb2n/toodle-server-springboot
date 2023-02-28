package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.project.Project;
import com.example.toodle_server_springboot.domain.project.task.Task;
import com.example.toodle_server_springboot.domain.project.task.action.Action;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.ActionDto;
import com.example.toodle_server_springboot.dto.project.ProjectDto;
import com.example.toodle_server_springboot.dto.project.TaskDto;
import com.example.toodle_server_springboot.repository.project.ActionRepository;
import com.example.toodle_server_springboot.repository.project.ProjectRepository;
import com.example.toodle_server_springboot.repository.project.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("비즈니스 로직 - 프로젝트")
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks private ProjectService sut;

    @Mock private ProjectRepository projectRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private ActionRepository actionRepository;

    private static UserAccount userAccount;
    private static final String testEmail = "testEmail";
    private static final String testNickName = "testNickName";
    private static final String testPwd = "testPwd";
    private static UUID projectId;
    private static UUID taskId;
    private static UUID actionId;
    private static ProjectDto projectDto;
    private static TaskDto taskDto;
    private static ActionDto actionDto;
    private static Project projectEntity;
    private static Task taskEntity;
    private static Action actionEntity;

    @BeforeEach
    void init() {
        userAccount = UserAccount.of(testEmail, testNickName, testPwd);
        projectId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        actionId = UUID.randomUUID();
        projectDto = new ProjectDto(projectId, UserAccountDto.from(userAccount), "project-test", new HashSet<>());
        taskDto = new TaskDto(taskId, UserAccountDto.from(userAccount), "task-test", new HashSet<>());
        actionDto = new ActionDto(actionId, UserAccountDto.from(userAccount), "action-test", LocalDateTime.now().plusDays(1L),false);
        projectEntity = projectDto.toEntity(userAccount);
        taskEntity = taskDto.toEntity(userAccount, projectEntity);
        actionEntity = actionDto.toEntity(userAccount, taskEntity);
    }

    @DisplayName("프로젝트 등록 테스트 - 성공 ")
    @Test
    void givenProjectDto_whenRegisterProject_thenReturnProject() {
        //given
        given(projectRepository.save(any(Project.class))).willReturn(Project.of(userAccount, "project-test"));

        //when
        var savedProject = sut.registerProject(userAccount, projectDto);

        //then
        verify(projectRepository).save(any(Project.class));
        assertThat(savedProject).isNotNull();
    }

    @DisplayName("프로젝트, 태스크 등록 테스트 - 성공")
    @Test
    void givenProjectDtoWithTask_whenRegisterProject_thenReturnProject() {
        //given
        projectDto.taskDtoSet().add(taskDto);
        given(projectRepository.save(any(Project.class))).willReturn(Project.of(userAccount, "project-test"));
        given(taskRepository.save(any(Task.class))).willReturn(Task.of(userAccount, any(),"task-test"));

        //when
        var savedProject = sut.registerProject(userAccount, projectDto);

        //then
        verify(projectRepository).save(any(Project.class));
        verify(taskRepository).save(any(Task.class));
        assertThat(savedProject).isNotNull();
    }

    @DisplayName("프로젝트, 태스크, 액션 등록 테스트 - 성공")
    @Test
    void givenProjectDtoWithTaskWithAction_whenRegisterProject_thenReturnProject() {
        //given
        taskDto.actionDtoSet().add(actionDto);
        projectDto.taskDtoSet().add(taskDto);
        given(projectRepository.save(any(Project.class))).willReturn(projectEntity);
        given(taskRepository.save(any(Task.class))).willReturn(taskEntity);
        given(actionRepository.save(any(Action.class))).willReturn(actionEntity);

        //when
        var savedProject = sut.registerProject(userAccount, projectDto);

        //then
        verify(projectRepository).save(any(Project.class));
        verify(taskRepository).save(any(Task.class));
        verify(actionRepository).save(any(Action.class));
        assertThat(savedProject).isNotNull();
    }

    @DisplayName("프로젝트 수정 테스트 - 성공")
    @Test
    void givenProjectDto_whenUpdateProject_thenReturnProject() {
        //given
        projectDto = new ProjectDto(projectId, UserAccountDto.from(userAccount), "project-test2", new HashSet<>());
        given(projectRepository.findById(projectId)).willReturn(Optional.of(projectEntity));

        //when
        var updatedProject = sut.updateProject(userAccount, projectDto);

        //then
        verify(projectRepository).findById(any(UUID.class));
        assertThat(updatedProject).isNotNull();
        assertEquals("project-test2", updatedProject.projectName());
    }

    @DisplayName("프로젝트, 테스크 수정 테스트 - 성공")
    @Test
    void givenProjectWithTaskDto_whenUpdateProject_thenReturnProject() {
        //given
        projectDto = new ProjectDto(projectId, UserAccountDto.from(userAccount), "project-test2", new HashSet<>());
        projectDto.taskDtoSet().add(taskDto);
        given(projectRepository.findById(projectId)).willReturn(Optional.of(projectEntity));
        given(taskRepository.save(any(Task.class))).willReturn(taskEntity);

        //when
        var updatedProject = sut.updateProject(userAccount, projectDto);

        //then
        verify(projectRepository).findById(any(UUID.class));
        verify(taskRepository).save(any(Task.class));
        assertThat(updatedProject).isNotNull();
        assertEquals("project-test2", updatedProject.projectName());
        assertEquals(1, updatedProject.taskDtoSet().size());
    }

    @DisplayName("프로젝트, 태스크, 액션 수정 테스트 - 성공")
    @Test
    void givenProjectWithTaskWithActionDto_whenUpdateProject_thenReturnProject() {
        //given


        //when

        //then

    }

    @DisplayName("프로젝트 삭제 테스트 - 성공")
    @Test
    void givenDeleteProjectDto_whenDeleteProject_thenReturnVoid() {
        //given


        //when

        //then

    }


}