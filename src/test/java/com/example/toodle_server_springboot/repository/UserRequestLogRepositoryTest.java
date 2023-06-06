package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.config.TestJpaConfig;
import com.example.toodle_server_springboot.domain.log.UserRequestLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그 DB JPA 연결 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestJpaConfig.class})
@ActiveProfiles("test")
class UserRequestLogRepositoryTest {

    private final UserRequestLogRepository userRequestLogRepository;

    UserRequestLogRepositoryTest(@Autowired UserRequestLogRepository userRequestLogRepository) {
        this.userRequestLogRepository = userRequestLogRepository;
    }

    @DisplayName("sql insert 결과 확인")
    @Test
    void givenTestSql_whenSelecting_thenReturnUserRequestLog() {
        //given
        UserRequestLog log = UserRequestLog.of("test-email", "requestURL", HttpMethod.GET, HttpStatus.OK, "user-request", "user-response");
        userRequestLogRepository.save(log);

        //when
        var logEntity = userRequestLogRepository.findById(log.getLogId());

        //then
        assertThat(logEntity)
                .isNotNull()
                .isNotEmpty();
    }

}