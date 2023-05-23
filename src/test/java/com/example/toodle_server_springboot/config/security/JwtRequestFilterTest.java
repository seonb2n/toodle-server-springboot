package com.example.toodle_server_springboot.config.security;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.request.UserAccountAuthenticateRequest;
import com.example.toodle_server_springboot.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class JwtRequestFilterTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean private UserAccountService userAccountService;
    private final JwtUserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userAccountService);

    JwtRequestFilterTest(
            @Autowired MockMvc mockMvc,
            @Autowired ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("사용자가 로그인 요청을 보내면 필터를 통과한다.")
    @Test
    void givenUserApiRequest_whenValidateJwtToken_thenReturnSuccess() throws Exception {
        //given
        UserAccountDto userAccountDto = UserAccountDto.of("test-email", "test-pw", "test-name");
        given(userAccountService.findUserAccountDto(any())).willReturn(Optional.of(userAccountDto));

        //when
        UserAccountAuthenticateRequest request = new UserAccountAuthenticateRequest("test-email", "test-pw");
        String content = objectMapper.writeValueAsString(request);
        var response = mockMvc.perform(post("/api/auth/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then


    }

}