package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.exception.CustomException;
import com.example.toodle_server_springboot.exception.ErrorCode;
import com.example.toodle_server_springboot.exception.UserEmailNotFoundException;
import com.example.toodle_server_springboot.exception.UserEmailSendFailException;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import com.example.toodle_server_springboot.util.MailBodyForm;
import com.example.toodle_server_springboot.util.TmpPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final JavaMailSender javaMailSender;
    private final TmpPasswordGenerator tmpPasswordGenerator;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 사용자 회원 가입에 사용하는 메서드
     *
     * @param userEmail
     * @param userNickname
     * @param userPassword
     * @return
     */
    @Transactional
    public UserAccountDto registerUser(String userEmail, String userPassword, String userNickname) {
        var preUserAccount = userAccountRepository.findUserAccountByEmail(userEmail);
        if (preUserAccount.isPresent()) {
            // 이미 등록된 Email Exception 발생
            throw new CustomException(ErrorCode.INVALID_EMAIL_EXCEPTION);
        }
        //todo 사용자 닉네임이 비어있으면 임의의 닉네임 부여하는 로직 추가!
        UserAccount userAccount = UserAccount.of(userEmail, userNickname, "{noop}" + userPassword);
        var savedUserAccount = userAccountRepository.save(userAccount);
        return UserAccountDto.from(userAccountRepository.save(savedUserAccount));
    }

    /**
     * UserEmail 을 사용해서 UserAccountDto 를 가져오는 메서드
     *
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<UserAccountDto> findUserAccountDto(String userEmail) {
        return userAccountRepository.findUserAccountByEmail(userEmail).map(UserAccountDto::from);
    }

    /**
     * UserEmail 을 사용해서 Entity 를 가져오는 메서드
     *
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public UserAccount findUserAccount(String userEmail) {
        return userAccountRepository.findUserAccountByEmail(userEmail).orElseThrow();
    }

    /**
     * 가입된 전적이 있는 이메일인지 체크한다.
     *
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkEmail(String userEmail) {
        return userAccountRepository.findUserAccountByEmail(userEmail).isPresent();
    }

    /**
     * 가입된 전적이 있는 회원의 경우, 비밀번호 변경 이메일을 보낸다.
     * @param userEmail
     */
    @Transactional
    public void sendEmailToUser(String userEmail) {
        UserAccount userAccount = userAccountRepository.findUserAccountByEmail(userEmail)
                .orElseThrow(UserEmailNotFoundException::new);

        // 1. 사용자 계정을 임시 비밀번호로 바꾼 뒤, 해당 계정의 상태를 임시 비번 상태로 바꾼다.
        String tmpPassword = tmpPasswordGenerator.generatePassword(10);
        userAccount.setPassword("{noop}" + tmpPassword);
        userAccount.setTmpPassword(true);
        userAccountRepository.save(userAccount);

        try {
            // 2. 해당 비밀번호를 안내하는 메일을 보낸다.
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("[TOODLE] 임시 비밀번호 안내");
            StringBuilder body = new StringBuilder();
            body.append(new MailBodyForm(userAccount.getNickname(), "비밀번호가 변경됐습니다. [" + userAccount.getPassword().substring(6) + "] \n 재로그인 부탁드립니다."));
            mimeMessageHelper.setText(body.toString(), true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new UserEmailSendFailException();
        }
    }
}
