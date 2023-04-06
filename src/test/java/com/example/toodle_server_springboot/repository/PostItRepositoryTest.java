package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.config.TestJpaConfig;
import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("포스트잇 JPA 연결 태스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestJpaConfig.class})
@ActiveProfiles("test")
class PostItRepositoryTest {

    private final PostItRepository postItRepository;
    private final PostItCategoryRepository postItCategoryRepository;
    private final UserAccountRepository userAccountRepository;
    private final TestEntityManager em;
    private static UserAccount userAccount;
    private static PostItCategory category;

    PostItRepositoryTest(
            @Autowired PostItRepository postItRepository,
            @Autowired PostItCategoryRepository postItCategoryRepository,
            @Autowired UserAccountRepository userAccountRepository,
            @Autowired TestEntityManager em
    ) {
        this.postItRepository = postItRepository;
        this.postItCategoryRepository = postItCategoryRepository;
        this.userAccountRepository = userAccountRepository;
        this.em = em;
    }

    @BeforeEach
    void setUp() {
        userAccount = userAccountRepository.save(UserAccount.of("testEmail", "testName", "testPw"));
        category = postItCategoryRepository.save(PostItCategory.of("test-postit-category", userAccount));
    }

    @DisplayName("postIt 생성 test")
    @Test
    void givenPostIt_whenInserting_thenReturnPostIt() {
        //given
        PostIt postIt = PostIt.of("content", userAccount);
        postIt.setPostICategory(category);

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
        postIt1.setPostICategory(category);
        postIt2.setPostICategory(category);
        var postItList = postItRepository.saveAll(List.of(postIt1, postIt2));

        //when
        postIt1.setContent("content-update!");
        var updatedPostItList = postItRepository.saveAll(List.of(postIt1, postIt2));

        //then
        assertEquals(2, postItList.size());
        assertEquals(2, updatedPostItList.size());
        assertThat(updatedPostItList.get(0).getContent()).contains("update");
    }

    @DisplayName("postItCategory 로 포스트잇의 조회가 가능한지 test")
    @Test
    public void givenPostItCategory_whenFindPostItCategory_thenReturnPostItList() throws Exception {
        //given
        PostIt postIt1 = PostIt.of("content", userAccount);
        PostIt postIt2 = PostIt.of("content", userAccount);
        postIt1.setPostICategory(category);
        postIt2.setPostICategory(category);
        postItRepository.saveAll(List.of(postIt1, postIt2));

        //when
        var savedPostItList = postItRepository.findAllByPostICategoryAndUserAccount_Email(category, userAccount.getEmail());

        //then
        assertEquals(2, savedPostItList.size());
    }

    //todo 포스트잇 카테고리 삭제 로직 구현 시, 테스트 케이스 추가
//    @DisplayName("postItCategory 를 삭제하는 경우, postIt 이 고아 객체로 남는지 test")
//    @Test
//    @Rollback(value = false)
//    public void givenNothing_whenDeletePostItCategory_thenReturnOrphanPostItList() throws Exception {
//        //given
//        PostItCategory deleteCategory = PostItCategory.of("test-postit-category2", userAccount);
//        postItCategoryRepository.save(deleteCategory);
//        PostIt postIt1 = PostIt.of("content", userAccount);
//        postIt1.setPostICategory(deleteCategory);
//        postItRepository.save(postIt1);
//
//        //when
//        postItCategoryRepository.delete(deleteCategory);
//        em.flush();
//        em.clear();
//        //var savedPostIt = em.find(PostIt.class, postIt1.getPostItId());
//        var savedPostIt = postItRepository.findById(postIt1.getPostItId());
//
//        //then
//        assertNotNull(savedPostIt.get().getPostICategory());
//        assertEquals(true, savedPostIt.get().getPostICategory().isDeleted());
//    }
//
//    @DisplayName("postItCategory 을 조회한다. 삭제된 포스트잇은 조회되지 않는다.")
//    @Test
//    public void givenUserAccount_whenFindPostItCategoryList_thenReturnExistPostItCategory() throws Exception {
//        //given
//        PostItCategory deleteCategory = PostItCategory.of("test-postit-category2", userAccount);
//        postItCategoryRepository.save(deleteCategory);
//        postItCategoryRepository.delete(deleteCategory);
//        em.flush();
//        em.clear();
//
//        //when
//        var postItList = postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(userAccount.getEmail());
//
//        //then
//        assertEquals(2, postItList.size());
//    }

}