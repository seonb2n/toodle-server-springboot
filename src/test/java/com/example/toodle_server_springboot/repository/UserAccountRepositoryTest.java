package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("사용자 계정 JPA 연결 태스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserAccountRepositoryTest {

    private final UserAccountRepository userAccountRepository;

    UserAccountRepositoryTest(@Autowired UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("sql insert 결과 확인 test")
    @Test
    public void givenTestSql_whenSelecting_thenReturnUser() throws Exception {
        //given

        //when
        List<UserAccount> userAccountList = userAccountRepository.findAll();

        //then
        assertThat(userAccountList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(1);
    }

    @DisplayName("Email 을 통한 사용자 조회 test")
    @Test
    public void givenUserEmail_whenSearch_thenReturnUser() throws Exception {
        //given
        String userEmail = userAccountRepository.findById(1L).get().getEmail();

        //when
        var userEntity = userAccountRepository.findUserAccountByEmail(userEmail);

        //then
        assertThat(userEntity)
                .isNotNull()
                .isNotEmpty();
    }
}