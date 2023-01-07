package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.dto.request.UserAccountRegisterRequest;
import com.example.toodle_server_springboot.dto.response.UserAccountResponse;
import com.example.toodle_server_springboot.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    /**
     * 사용자 회원 가입 호출 컨트롤러
     * @param request
     * @return
     */
    @PostMapping("/register")
    public UserAccountResponse registerUser(@RequestBody UserAccountRegisterRequest request) {
        var registerDto = userAccountService.registerUser(request.userEmail(), request.userPassword(), request.userNickName());
        return UserAccountResponse.from(registerDto);
    }
}
