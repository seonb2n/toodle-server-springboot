package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.dto.response.project.ProjectResponse;
import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import com.example.toodle_server_springboot.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 사용자가 가지고 있는 모든 프로젝트를 내려준다.
     * @param userPrincipal
     * @return
     */
    @GetMapping
    public ResponseEntity<ProjectResponse> findAllProject(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        var projectDtoList = projectService.findAllProject(userPrincipal.toDto());
        var projectResponse = ProjectResponse.of(projectDtoList);
        return ResponseEntity.ok(projectResponse);
    }

    /**
     * 프로젝트를 업데이트 한다.
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<ProjectResponse> updateProject() {
        return ResponseEntity.ok(null);
    }

    /**
     * 프로젝트를 등록한다.
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ProjectResponse> registerProject() {
        return ResponseEntity.ok(null);
    }

    /**
     * 프로젝트를 삭제한다.
     * @return
     */
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject() {
        return ResponseEntity.ok("삭제 성공");
    }
}
