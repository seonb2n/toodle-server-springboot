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
    private final UserAccountRepository userAccountRepository;
    private static UserAccount userAccount;
    private static PostItCategory category;

    PostItRepositoryTest(
            @Autowired PostItRepository postItRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.postItRepository = postItRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @BeforeEach
    void setUp() {
        String userEmail = "testEmail";
        userAccount = userAccountRepository.save(UserAccount.of(userEmail, "testName", "testPw"));
        category =PostItCategory.of("test-postit-category", userAccount);
//        em.flush();
    }

    @DisplayName("postIt 생성 test")
    @Test
    void givenPostIt_whenInserting_thenReturnPostIt() {
        //given
        PostIt postIt = PostIt.of("content", category, userAccount);
        category.addPostIt(postIt);

        //when
        var savedPostIt = postItRepository.save(postIt);

        //then
        assertThat(savedPostIt.getPostItClientId()).isNotNull();
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
//
//    @DisplayName("postItCategory 로 포스트잇의 조회가 가능한지 test")
//    @Test
//    public void givenPostItCategory_whenFindPostItCategory_thenReturnPostItList() throws Exception {
//        //given
//        PostIt postIt1 = PostIt.of("content", userAccount);
//        PostIt postIt2 = PostIt.of("content", userAccount);
//        postIt1.setPostICategory(category);
//        postIt2.setPostICategory(category);
//        postItRepository.saveAll(List.of(postIt1, postIt2));
//
//        //when
//        var savedPostItList = postItRepository.findAllByPostICategoryAndUserAccount_Email(category, userEmail);
//
//        //then
//        assertEquals(2, savedPostItList.size());
//    }
//
//    @DisplayName("postItCategory 를 삭제하는 경우라도, postIt 에서는 postItCategory 의 정보를 가져올 수 있다. 그러나 해당 포스트잇 카테고리의 상태는 삭제된 상태이다.")
//    @Test
//    public void givenNothing_whenDeletePostItCategory_thenReturnOrphanPostItList() throws Exception {
//        //given
//        PostIt postIt1 = PostIt.of("content", userAccount);
//        postIt1.setPostICategory(category);
//        postIt1 = postItRepository.save(postIt1);
//
//        //when
//        postItCategoryRepository.deleteByPostItCategoryClientId(category.getPostItCategoryClientId());
////        em.flush();
//        postIt1 = postItRepository.getReferenceById(postIt1.getPostItId());
//
//        //then
//        assertNotNull(postIt1.getPostICategory());
//        assertTrue(postIt1.getPostICategory().isDeleted());
//    }
//
//    @DisplayName("postItCategory 을 조회한다. 삭제된 포스트잇은 조회되지 않는다.")
//    @Test
//    @Transactional
//    public void givenUserAccount_whenFindPostItCategoryList_thenReturnExistPostItCategory() throws Exception {
//        //given
//        PostItCategory deleteCategory = PostItCategory.of("test-postit-category2", userAccount);
//        deleteCategory = postItCategoryRepository.save(deleteCategory);
//        postItCategoryRepository.deleteByPostItCategoryClientId(deleteCategory.getPostItCategoryClientId());
//        em.flush();
//        em.clear();
//
//        //when
//        var postItList = postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(userEmail);
//
//        //then
//        assertEquals(1, postItList.size());
//    }

}