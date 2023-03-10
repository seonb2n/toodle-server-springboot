package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 프로젝트")
@Import({TestSecurityConfig.class})
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    private final MockMvc mvc;
    @MockBean private ProjectService projectService;

    ProjectControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
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

}