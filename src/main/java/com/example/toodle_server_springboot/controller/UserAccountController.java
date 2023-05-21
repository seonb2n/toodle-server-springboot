package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.security.JwtTokenUtil;
import com.example.toodle_server_springboot.config.security.JwtUserDetailsService;
import com.example.toodle_server_springboot.dto.request.UserAccountAuthenticateRequest;
import com.example.toodle_server_springboot.dto.request.UserAccountChangePasswordRequest;
import com.example.toodle_server_springboot.dto.request.UserAccountRegisterRequest;
import com.example.toodle_server_springboot.dto.response.UserAccountAuthenticateResponse;
import com.example.toodle_server_springboot.dto.response.UserAccountResponse;
import com.example.toodle_server_springboot.repository.SessionRepository;
import com.example.toodle_server_springboot.service.UserAccountService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final SessionRepository sessionRepository;


    /**
     * 사용자 회원 가입 호출 컨트롤러
     *
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
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(
            @RequestBody UserAccountAuthenticateRequest request,
            @NotNull HttpSession session
    ) throws Exception {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authenticationToken);
        var userDetails = jwtUserDetailsService.loadUserByUsername(request.email());
        sessionRepository.saveSession(session, request.email());
        var token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new UserAccountAuthenticateResponse(token));
    }

    /**
     * 네이버 아이디로 회원가입 및 로그인을 진행한 사용자의 경우에 적절한 토큰을 발급한다.
     * @param code
     * @return
     */
    @RequestMapping(value = "/login/naver", method = RequestMethod.GET)
    public ResponseEntity<?> redirectUserLoginWithNaver(@RequestParam String code) {
        return ResponseEntity.ok(code);
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
