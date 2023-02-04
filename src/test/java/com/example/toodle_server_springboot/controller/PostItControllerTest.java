package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.dto.PostItDto;
import com.example.toodle_server_springboot.service.PostItService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 포스트잇")
@Import({TestSecurityConfig.class})
@WebMvcTest(PostItController.class)
class PostItControllerTest {

    private final MockMvc mvc;
    @MockBean private PostItService postItService;

    PostItControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
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
        PostItDto testPostItDto1 = createPostIt("test", LocalDateTime.now().plusDays(1L), false);
        PostItDto testPostItDto2 = createPostIt("test", LocalDateTime.now().plusDays(2L), true);
        given(postItService.getAllPostIt(any())).willReturn(List.of(testPostItDto1, testPostItDto2));

        //when
        mvc.perform(get("/api/v1/postits"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", equalTo(2)));

        //then
        then(postItService).should().getAllPostIt(any());
    }

    public PostItDto createPostIt(String content, LocalDateTime endDay, boolean isDone) {
        return PostItDto.of(content, endDay, isDone);
    }
}