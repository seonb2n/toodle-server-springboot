package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.repository.project.ActionRepository;
import com.example.toodle_server_springboot.repository.project.ProjectRepository;
import com.example.toodle_server_springboot.repository.project.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 프로젝트")
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks private ProjectService sut;

    @Mock private ProjectRepository projectRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private ActionRepository actionRepository;

    @DisplayName("프로젝트 등록 테스트 - 성공 ")
    @Test
    void givenProjectDto_whenRegisterProject_thenReturnProject() {
        //given

        //when

        //then

    }

    @DisplayName("프로젝트, 태스크 등록 테스트 - 성공")
    @Test
    void givenProjectDtoWithTask_whenRegisterProject_thenReturnProject() {
        //given


        //when

        //then

    }

    @DisplayName("프로젝트, 태스크, 액션 등록 테스트 - 성공")
    @Test
    void givenProjectDtoWithTaskWithAction_whenRegisterProject_thenReturnProject() {
        //given


        //when

        //then

    }

    @DisplayName("프로젝트 수정 테스트 - 성공")
    @Test
    void givenProjectDto_whenUpdateProject_thenReturnProject() {
        //given


        //when

        //then

    }

    @DisplayName("프로젝트, 테스크 수정 테스트 - 성공")
    @Test
    void givenProjectWithTaskDto_whenUpdateProject_thenReturnProject() {
        //given


        //when

        //then

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