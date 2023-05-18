package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.repository.PostItCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@EnableJpaAuditing
@DisplayName("비즈니스 로직 - 포스트잇 카테고리")
@ExtendWith(MockitoExtension.class)
class PostItCategoryServiceTest {

    @InjectMocks
    private PostItCategoryService sut;

    @Mock
    private PostItCategoryRepository postItCategoryRepository;

    private static UserAccount userAccount;
    private static final String testEmail = "testEmail";
    private static final String testNickName = "testNickName";
    private static final String testPwd = "testPwd";
    private static PostItCategory postItCategoryEntity1;

    @BeforeEach
    void init() {
        userAccount = UserAccount.of(testEmail, testNickName, testPwd);
        postItCategoryEntity1 = PostItCategory.of("test-category1", userAccount);
    }

    @DisplayName("사용자가 보유한 모든 포스트잇 카테고리를 가져오되, 삭제된 카테고리는 가져오지 않는다.")
    @Test
    public void givenFindAllPostItCategory_whenFindAllPostItCategory_thenReturnPostItCategoryNotDeleted() throws Exception {
        //given
        given(postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(any())).willReturn(List.of(postItCategoryEntity1));

        //when
        var result = sut.findAllPostIt(userAccount);

        //then
        assertEquals(1, result.size());
    }

    @DisplayName("포스티잇 카테고리를 삭제한다.")
    @Test
    public void givenPostItCategoryDeleteRequest_whenDeletePostItCategory_thenNull() throws Exception {
        //given
        postItCategoryEntity1.markAsDeleted();
        given(postItCategoryRepository.findByPostItCategoryClientId(any())).willReturn(Optional.of(postItCategoryEntity1));

        //when
        sut.deleteCategory(postItCategoryEntity1.getPostItCategoryClientId());

        //then
        assertTrue(postItCategoryEntity1.isDeleted());
    }

}