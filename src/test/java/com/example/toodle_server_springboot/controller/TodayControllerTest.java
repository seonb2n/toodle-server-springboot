package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;
import com.example.toodle_server_springboot.dto.project.ProjectDto;
import com.example.toodle_server_springboot.dto.response.today.TodayResponse;
import com.example.toodle_server_springboot.service.PostItService;
import com.example.toodle_server_springboot.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(("View 컨트롤러 - Today"))
@Import({TestSecurityConfig.class})
@WebMvcTest(TodayController.class)
class TodayControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;
    @MockBean
    private PostItService postItService;

    TodayControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @WithUserDetails(value = "sbkim@naver.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[GET] Today 화면 구성에 필요한 데이터 조회")
    @Test
    public void givenUserAccount_whenGetToday_thenReturnTodayResponse() throws Exception {
        //given
        UserAccountDto userAccountDto = UserAccountDto.of("test-email", "test-nickname", "test-pwd");
        ProjectDto testProjectDto = ProjectDto.of(UUID.randomUUID(), userAccountDto, "test-project", "#ffffff", Set.of());
        PostItDto postItDto = PostItDto.of(UUID.randomUUID(), null, "postit-contentDtoList", LocalDateTime.now(), false);
        given(projectService.findAllProject(any())).willReturn(List.of(testProjectDto));
        given(postItService.getAllPostIt(any())).willReturn(List.of(postItDto));

        //when
        MvcResult result = mvc.perform(get("/api/vi/today"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String responseBody = result.getResponse().getContentAsString();
        TodayResponse todayResponse = objectMapper.readValue(responseBody, TodayResponse.class);

        assertEquals(1, todayResponse.projectResponse().projectDtoList().size());
        assertEquals(1, todayResponse.todayPostItResponse().contentDtoList().size());
    }

}