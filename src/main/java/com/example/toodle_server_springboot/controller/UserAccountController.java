package com.example.toodle_server_springboot.controller;

import com.example.toodle_server_springboot.config.security.JwtTokenUtil;
import com.example.toodle_server_springboot.config.security.JwtUserDetailsService;
import com.example.toodle_server_springboot.dto.request.UserAccountAuthenticateRequest;
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
     * @param request
     * @return
     */
    @PostMapping("/register")
    public UserAccountResponse registerUser(@RequestBody UserAccountRegisterRequest request) {
        var registerDto = userAccountService.registerUser(request.userEmail(), request.userPassword(), request.userNickName());
        return UserAccountResponse.from(registerDto);
    }

    /**
     * 사용자가 email 과 password 를 보낸 경우, 인증된 사용자라면 적절한 토큰을 발급한다.
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody UserAccountAuthenticateRequest request) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var userDetails = jwtUserDetailsService.loadUserByUsername(request.email());
        var token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new UserAccountAuthenticateResponse(token));
    }
}
