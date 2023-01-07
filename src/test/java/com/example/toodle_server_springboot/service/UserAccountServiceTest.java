package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.UserAccount;
import com.example.toodle_server_springboot.exception.CustomException;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 사용자")
@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks private UserAccountService sut;

    @Mock private UserAccountRepository userAccountRepository;

    @DisplayName("회원 가입을 요청하면, 다른 Email 일 경우 허용한다.")
    @Test
    public void givenRegisterRequest_whenRegister_thenSuccessRegister() throws Exception {
        //given
        String email = "test@example.com";
        String password = "test123456";
        String nickname = "test-user";
        given(userAccountRepository.save(any(UserAccount.class))).willReturn(
                UserAccount.of(email, password, nickname)
        );

        //when
        var userDto = sut.registerUser(email, password, nickname);

        //then
        assertThat(userDto).isNotNull();
    }

    @DisplayName("회원 가입을 요청하면, 다른 Email 일 경우 거부한다.")
    @Test
    public void givenRegisterRequest_whenRegister_thenFailRegister() throws Exception {
        //given
        String email = "sbkim@example.com";
        String password = "test123456";
        String nickname = "test-user";
        given(userAccountRepository.findUserAccountByEmail(email)).willReturn(
                Optional.of(UserAccount.of(email, password, nickname))
        );

        //when & then
        assertThrows(CustomException.class,() -> sut.registerUser(email, password, nickname));
    }
}