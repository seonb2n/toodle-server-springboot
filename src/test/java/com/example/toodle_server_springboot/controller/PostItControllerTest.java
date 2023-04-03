package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;
import com.example.toodle_server_springboot.dto.request.PostItUpdateRequest;
import com.example.toodle_server_springboot.service.PostItService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 포스트잇")
@Import({TestSecurityConfig.class})
@WebMvcTest(PostItController.class)
class PostItControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;
    @MockBean private PostItService postItService;

    PostItControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper objectMapper
    ) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[GET] 포스트잇 조회 페이지 - 인증 없을 땐 401 unAuthorized")
    @Test
    void givenNothing_whenRequesting_thenReturn401() throws Exception{
        //given

        //when
        mvc.perform(get("/api/v1/postits"))
                .andExpect(status().is4xxClientError());

        //then
        then(postItService).shouldHaveNoInteractions();
    }

    @WithUserDetails(value = "sbkim@naver.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[GET] 포스트잇 조회 페이지 - 정상 호출, 인증된 사용자")
    @Test
    void givenAuthorizedUser_whenRequesting_thenReturnPostItList() throws Exception {
        //given
        PostItDto testPostItDto1 = createPostIt("test", false);
        PostItDto testPostItDto2 = createPostIt("test", true);
        given(postItService.getAllPostIt(any())).willReturn(List.of(testPostItDto1, testPostItDto2));

        //when
        mvc.perform(get("/api/v1/postits"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", equalTo(2)));

        //then
        then(postItService).should().getAllPostIt(any());
    }

    @WithUserDetails(value = "sbkim@naver.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[POST] 포스트잇 업데이트 페이지 - 정상 호출, 인증된 사용자")
    @Test
    void givenPostItUpdate_whenUpdatePostIt_thenReturnUpdatedPostIt() throws Exception {
        //given
        PostItCategoryDto testCategoryDto = createPostItCategoryDto("test-category");
        PostItDto testPostItDto = createPostIt("test", false);
        var postItUpdateRequest = objectMapper.writeValueAsString(PostItUpdateRequest.of(List.of(testCategoryDto),List.of(testPostItDto)));
        given(postItService.updatePostIt(any(), any(), any())).willReturn(List.of(testPostItDto));

        //when
        mvc.perform(post("/api/v1/postits/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postItUpdateRequest)
                )
                .andExpect(status().isOk())
        ;

        //then
        then(postItService).should().updatePostIt(any(), any(), any());
    }

    public PostItCategoryDto createPostItCategoryDto(String title) {
        return PostItCategoryDto.of(title);
    }

    public PostItDto createPostIt(String content, boolean isDone) {
        PostItCategoryDto categoryDto = createPostItCategoryDto("test-postit-categoriy");
        return PostItDto.of(categoryDto, content, isDone);
    }
}