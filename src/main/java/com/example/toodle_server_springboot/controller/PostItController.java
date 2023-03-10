package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.dto.PostItDto;
import com.example.toodle_server_springboot.dto.request.PostItUpdateRequest;
import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import com.example.toodle_server_springboot.service.PostItService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/postits")
@RequiredArgsConstructor
public class PostItController {

    private final PostItService postItService;

    /**
     * 사용자가 보유한 모든 포스트잇을 내려준다.
     * @param userPrincipal
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PostItDto>> findAllPostIt(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        var postItDtoList = postItService.getAllPostIt(userPrincipal.toDto());
        return ResponseEntity.ok(postItDtoList);
    }

    /**
     * 사용자의 포스트잇을 서버에 저장한다.
     * @param updateRequest
     * @param userPrincipal
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<List<PostItDto>> updateAllPostIt(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PostItUpdateRequest updateRequest
            ) {
        var updatedPostItDtoList = postItService.updatePostIt(updateRequest.getPostItDtoList(), userPrincipal.toDto());
        return ResponseEntity.ok(updatedPostItDtoList);
    }

}
