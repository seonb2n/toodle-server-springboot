package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.exception.CustomException;
import com.example.toodle_server_springboot.exception.ErrorCode;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    /**
     * 사용자 회원 가입에 사용하는 메서드
     * @param userEmail
     * @param userNickname
     * @param userPassword
     * @return
     */
    public UserAccountDto registerUser(String userEmail, String userNickname, String userPassword) {
        var preUserAccount = userAccountRepository.findUserAccountByEmail(userEmail);
        if (preUserAccount.isPresent()) {
            // 이미 등록된 Email Exception 발생
            throw new CustomException(ErrorCode.INVALID_EMAIL_EXCEPTION);
        }
        var userAccount = userAccountRepository.save(UserAccount.of(userEmail, userNickname, userPassword));
        return UserAccountDto.from(userAccountRepository.save(userAccount));
    }

    /**
     * UserEmail 을 사용해서 DB 로부터 사용자 데이터를 가져오는 메서드
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<UserAccountDto> searchUser(String userEmail) {
        return userAccountRepository.findUserAccountByEmail(userEmail).map(UserAccountDto::from);
    }
}
