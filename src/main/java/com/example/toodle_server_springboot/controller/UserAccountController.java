package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.security.JwtTokenUtil;
import com.example.toodle_server_springboot.config.security.JwtUserDetailsService;
import com.example.toodle_server_springboot.dto.request.UserAccountAuthenticateRequest;
import com.example.toodle_server_springboot.dto.request.UserAccountChangePasswordRequest;
import com.example.toodle_server_springboot.dto.request.UserAccountRegisterRequest;
import com.example.toodle_server_springboot.dto.response.UserAccountAuthenticateResponse;
import com.example.toodle_server_springboot.dto.response.UserAccountResponse;
import com.example.toodle_server_springboot.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    /**
     * 사용자 회원 가입 호출 컨트롤러
     * @param userAccountRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public UserAccountResponse registerUser(@RequestBody UserAccountRegisterRequest userAccountRegisterRequest) {
        var registerDto = userAccountService.registerUser(userAccountRegisterRequest.userEmail(), userAccountRegisterRequest.userPassword(), userAccountRegisterRequest.userNickName());
        var userDetails = jwtUserDetailsService.loadUserByUsername(registerDto.email());
        var token = jwtTokenUtil.generateToken(userDetails);
        return UserAccountResponse.from(registerDto, token);
    }

    /**
     * 사용자가 email 과 password 를 보낸 경우, 인증된 사용자라면 적절한 토큰을 발급한다.
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody UserAccountAuthenticateRequest request) throws Exception{
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authenticationToken);
        var userDetails = jwtUserDetailsService.loadUserByUsername(request.email());
        var token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new UserAccountAuthenticateResponse(token));
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkUserAccountEmail(@RequestParam String userEmail) {
        return ResponseEntity.ok(userAccountService.checkEmail(userEmail));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody UserAccountChangePasswordRequest request) {
        userAccountService.sendEmailToUser(request.userEmail());
        return ResponseEntity.ok().build();
    }
}
