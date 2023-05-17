package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.config.TestJpaConfig;
import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("포스트잇 카테고리 JPA 연결 태스트")
@DataJpaTest
@Import({TestJpaConfig.class})
@ActiveProfiles("test")
@Transactional
class PostItCategoryRepositoryTest {

    private final PostItCategoryRepository postItCategoryRepository;
    private final UserAccountRepository userAccountRepository;
    private final EntityManager em;
    private static UserAccount userAccount;
    private static PostItCategory category;

    public PostItCategoryRepositoryTest(
            @Autowired PostItCategoryRepository postItCategoryRepository,
            @Autowired UserAccountRepository userAccountRepository,
            @Autowired EntityManager em
    ) {
        this.postItCategoryRepository = postItCategoryRepository;
        this.userAccountRepository = userAccountRepository;
        this.em = em;
    }

    @BeforeEach
    void setUp() {
        String userEmail = "testEmail";
        userAccount = userAccountRepository.save(UserAccount.of(userEmail, "testName", "testPw"));
    }

    @DisplayName("PostItCategory 를 생성 및 저장하면 조회할 수 있다.")
    @Test
    public void givenCreatePostItCategory_whenFindPostItCategory_thenReturnPostItCategory() throws Exception {
        //given
        PostItCategory postItCategory = PostItCategory.of("test-category", userAccount);
        postItCategoryRepository.save(postItCategory);

        //when
        var postItCategoryList = postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(userAccount.getEmail());

        //then
        assertEquals(1, postItCategoryList.size());
        assertEquals(postItCategory.getPostItCategoryId(), postItCategoryList.get(0).getPostItCategoryId());
    }

    @DisplayName("PostItCategory 를 생성 했으나, 삭제한 경우에는 isDeleted 가 업데이트된다.")
    @Test
    public void givenCreatePostItCategory_whenFindPostItCategory_thenReturnPostItAndCategory() throws Exception {
        // given
        PostItCategory postItCategory = PostItCategory.of("test-category", userAccount);
        postItCategoryRepository.save(postItCategory);

        PostItCategory myCategory = postItCategoryRepository.findById(postItCategory.getPostItCategoryId()).get();
        assertNotNull(myCategory);
        assertEquals(postItCategory.getPostItCategoryId(), myCategory.getPostItCategoryId());

        // update
        myCategory.markAsDeleted();
        postItCategoryRepository.save(myCategory);

        // then
        PostItCategory updatedCategory = postItCategoryRepository.findById(postItCategory.getPostItCategoryId()).get();
        assertNotNull(updatedCategory.getPostItCategoryId());
        assertTrue(updatedCategory.isDeleted());
    }
}