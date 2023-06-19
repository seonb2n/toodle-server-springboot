package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.request.project.ProjectRequest;
import com.example.toodle_server_springboot.dto.response.project.ProjectResponse;
import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import com.example.toodle_server_springboot.service.ProjectService;
import com.example.toodle_server_springboot.service.UserAccountService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserAccountService userAccountService;

    /**
     * 사용자가 가지고 있는 모든 프로젝트를 내려준다.
     *
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
     *
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<ProjectResponse> updateProject(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody ProjectRequest projectRequest
    ) {
        UserAccountDto userAccountDto = userPrincipal.toDto();
        UserAccount userAccount = userAccountService.findUserAccount(userAccountDto.email());
        var projectDto = projectRequest.toDto(userAccountDto);
        var updatedProject = projectService.updateProject(userAccount, projectDto);
        return ResponseEntity.ok(ProjectResponse.of(List.of(updatedProject)));
    }

    /**
     * 프로젝트를 등록한다.
     *
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ProjectResponse> registerProject(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody ProjectRequest projectRequest
    ) {
        UserAccountDto userAccountDto = userPrincipal.toDto();
        UserAccount userAccount = userAccountService.findUserAccount(userAccountDto.email());
        var projectDto = projectRequest.toDto(userAccountDto);
        var registeredProject = projectService.registerProject(userAccount, projectDto);
        return ResponseEntity.ok(ProjectResponse.of(List.of(registeredProject)));
    }

    /**
     * 프로젝트를 삭제한다.
     *
     * @return
     */
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable UUID projectId) {
        UserAccount userAccount = userAccountService.findUserAccount(userPrincipal.email());
        projectService.deleteProject(userAccount, projectId);
        return ResponseEntity.ok("삭제 성공");
    }
}
