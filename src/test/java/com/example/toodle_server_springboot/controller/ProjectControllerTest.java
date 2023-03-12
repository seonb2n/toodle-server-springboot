package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.project.ProjectDto;
import com.example.toodle_server_springboot.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 프로젝트")
@Import({TestSecurityConfig.class})
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    private final MockMvc mvc;
    @MockBean private ProjectService projectService;

    private static UUID projectUUID;
    private static ProjectDto projectDto;
    private static UserAccountDto userAccountDto;

    ProjectControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
        projectUUID = UUID.randomUUID();
        userAccountDto = UserAccountDto.of("test-email", "test-nickname", "test-pwd");
        projectDto = ProjectDto.of(projectUUID, userAccountDto, "test-pjt-name", Set.of());
    }

    @DisplayName("[GET]프로젝트 조회 - 인증 없을 땐 401 unAuthroized")
    @Test
    void givenNotrhing_whenRequestGetProject_thenReturn401() throws Exception{
        //given

        //when
        mvc.perform(get("/api/v1/projects"))
                .andExpect(status().is4xxClientError());

        //then
        then(projectService).shouldHaveNoInteractions();
    }

    @WithUserDetails(value = "sbkim@naver.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[GET] 프로젝트 조회 페이지 - 정상 호출, 인증된 사용자")
    @Test
    void givenAuthorizedUser_whenRequesting_thenReturnProjectList() throws Exception {
        //given

        //when
        mvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk());

        //then
        then(projectService).should().findAllProject(any());
    }

    @DisplayName("[POST] 프로젝트 업데이트 페이지 - 정상 호출, 인증된 사용자")
    @Test
    void givenAuthorizedUser_whenRequestUpdateProject_thenReturnProjectList() throws Exception {
        //given
        given(projectService.updateProject(any(), any())).willReturn(projectDto);

        //when
        mvc.perform(post("/api/v1/projects/update"))
                .andExpect(status().isOk())
                ;

        //then
        then(projectService).should().updateProject(any(), any());
    }

    @DisplayName("[POST] 프로젝트 등록 페이지 - 정상 호출, 인증된 사용자")
    @Test
    void givenAuthorizedUser_whenRequestRegisterProject_thenReturnProjectList() throws Exception {
        //given
        given(projectService.registerProject(any(), any())).willReturn(projectDto);

        //when
        mvc.perform(post("/api/v1/projects/register"))
                .andExpect(status().isOk())
                ;
        //then
        then(projectService).should().registerProject(any(), any());
    }

    @DisplayName("[POST] 프로젝트 삭제 페이지 - 정상 호출, 인증된 사용자")
    @Test
    void givenAuthorizedUser_whenRequetDeleteProject_thenReturnSuccessMsg() {
        //given


        //when

        //then

    }
}