package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.config.TestJpaConfig;
import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("포스트잇 JPA 연결 태스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestJpaConfig.class})
class PostItRepositoryTest {

    private final PostItRepository postItRepository;
    private final UserAccountRepository userAccountRepository;
    private static UserAccount userAccount;

    PostItRepositoryTest(
            @Autowired PostItRepository postItRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.postItRepository = postItRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @BeforeEach
    void setUp() {
        userAccount = userAccountRepository.save(UserAccount.of("testEmail", "testName", "testPw"));
    }

    @DisplayName("postIt 생성 test")
    @Test
    void givenPostIt_whenInserting_thenReturnPostIt() {
        //given
        PostIt postIt = PostIt.of("content", userAccount);

        //when
        var savedPostIt = postItRepository.save(postIt);

        //then
        assertThat(savedPostIt.getPostItId()).isNotNull();
    }

    @DisplayName("postIt 업데이트 test")
    @Test
    void givenPostItList_whenUpdate_thenReturnPostItList() {
        //given
        PostIt postIt1 = PostIt.of("content", userAccount);
        PostIt postIt2 = PostIt.of("content", userAccount);
        var postItList = postItRepository.saveAll(List.of(postIt1, postIt2));

        //when
        postIt1.setContent("content-update!");
        var updatedPostItList = postItRepository.saveAll(List.of(postIt1, postIt2));

        //then
        assertEquals(2, postItList.size());
        assertEquals(2, updatedPostItList.size());
        assertThat(updatedPostItList.get(0).getContent()).contains("update");
    }

}