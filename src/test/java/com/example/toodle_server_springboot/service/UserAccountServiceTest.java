package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.config.TestSecurityConfig;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.exception.CustomException;
import com.example.toodle_server_springboot.exception.UserEmailNotFoundException;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import com.example.toodle_server_springboot.util.TmpPasswordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("비즈니스 로직 - 사용자")
@ExtendWith(MockitoExtension.class)
@Import({TestSecurityConfig.class})
@TestPropertySource(properties = { "spring.mail.username=sbkim@naver.com" })
class UserAccountServiceTest {

    @Mock private UserAccountRepository userAccountRepository;
    @Mock private JavaMailSender javaMailSender;
    @Mock private TmpPasswordGenerator tmpPasswordGenerator;

    @InjectMocks private UserAccountService sut;


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

    @DisplayName("UserEmail 을 바탕으로 사용자 Dto 를 가져온다.")
    @Test
    void given_when_then() {
        //given
        String testEmail = "test-email";
        UserAccount userAccount = UserAccount.of(testEmail, "test-name", "test-pwd");
        given(userAccountRepository.findUserAccountByEmail(testEmail)).willReturn(Optional.of(userAccount));

        //when
        var result = sut.findUserAccountDto(testEmail);

        //then
        assertNotNull(result.get());
    }

    @DisplayName("UserEmail 을 바탕으로 사용자를 조회한다.")
    @Test
    void givenUserEmail_whenFindUser_thenReturnUserEntity() {
        //given
        String testEmail = "test-email";
        UserAccount userAccount = UserAccount.of(testEmail, "test-name", "test-pwd");
        given(userAccountRepository.findUserAccountByEmail(testEmail)).willReturn(Optional.of(userAccount));

        //when
        var result = sut.findUserAccount(testEmail);

        //then
        assertEquals(testEmail, result.getEmail());
    }

    @DisplayName("UserEmail 로 이미 존재하는 사용자인지 확인한다 - 존재")
    @Test
    void givenUserEmail_whenCheckUserEmail_thenReturnTrue() {
        //given
        String testEmail = "test-email";
        UserAccount userAccount = UserAccount.of(testEmail, "test-name", "test-pwd");
        given(userAccountRepository.findUserAccountByEmail(testEmail)).willReturn(Optional.of(userAccount));

        //when
        var result = sut.checkEmail(testEmail);

        //then
        assertTrue(result);
    }

    @DisplayName("userEmail 로 이미 존재하는 사용자인지 확인한다 - 존재하지 않음")
    @Test
    void givenUserEmail_whenCheckUserEmail_thenReturnFalse() {
        //given
        String testEmail = "test-email";
        given(userAccountRepository.findUserAccountByEmail(testEmail)).willReturn(Optional.empty());

        //when
        var result = sut.checkEmail(testEmail);

        //then
        assertFalse(result);
    }
    
    @DisplayName("userEmail 로 가입된 회원이 존재하는 경우, 비밀번호 변경 이메일을 전송한다")
    @Test
    public void givenUserEmail_whenUserAccountWithEmailExist_thenSendEmail() throws Exception {
        //given
        String email = "test@example.com";
        String password = "test123456";
        String nickname = "test-user";
        sut.setFrom("TEST_FROM@NAVER.COM");
        given(userAccountRepository.findUserAccountByEmail(email))
                .willReturn(Optional.of(UserAccount.of(email, password, nickname)));
        given(javaMailSender.createMimeMessage()).willReturn(new MimeMessage((Session) null));


        //when
        sut.sendEmailToUser(email);

        //then
        verify(userAccountRepository).findUserAccountByEmail(email);
    }

    @DisplayName("userEmail 로 가입된 회원이 존재하지 않는 경우, Error를 발생시킨다.")
    @Test
    public void givenUserEmail_whenUserAccountWithEmailNotExist_thenThrowError() throws Exception {
        //given
        String email = "test@example.com";
        given(userAccountRepository.findUserAccountByEmail(email))
                .willReturn(Optional.empty());

        //when & then
        assertThrows(UserEmailNotFoundException.class, () -> sut.sendEmailToUser(email));
        verify(userAccountRepository).findUserAccountByEmail(email);

    }
}