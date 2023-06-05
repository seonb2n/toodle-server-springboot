package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.dto.response.project.ProjectResponse;
import com.example.toodle_server_springboot.dto.response.today.TodayPostItResponse;
import com.example.toodle_server_springboot.dto.response.today.TodayResponse;
import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import com.example.toodle_server_springboot.service.PostItService;
import com.example.toodle_server_springboot.service.ProjectService;
import com.example.toodle_server_springboot.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * today 화면에서 요구되는 요청을 처리하는 controller
 */
@RestController
@RequestMapping("/api/v1/today")
@RequiredArgsConstructor
public class TodayController {

    private final ProjectService projectService;
    private final UserAccountService userAccountService;
    private final PostItService postItService;

    /**
     * today 화면 접속 시 보여줄 데이터 전송
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<TodayResponse> todayResponse(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        var projectDtoList = projectService.findAllProject(userPrincipal.toDto());
        var projectResponse = ProjectResponse.of(projectDtoList);
        var postItDtoList = postItService.getAllPostIt(userPrincipal.toDto());
        var todayResponse = TodayResponse.of(projectResponse, TodayPostItResponse.of(postItDtoList));
        return ResponseEntity.ok(todayResponse);
    }
}
