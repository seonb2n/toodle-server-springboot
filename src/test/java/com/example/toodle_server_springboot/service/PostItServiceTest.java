package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;
import com.example.toodle_server_springboot.repository.PostItCategoryRepository;
import com.example.toodle_server_springboot.repository.PostItRepository;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@EnableJpaAuditing
@DisplayName("비즈니스 로직 - 포스트잇")
@ExtendWith(MockitoExtension.class)
class PostItServiceTest {

    @InjectMocks
    private PostItService sut;

    @Mock
    private PostItRepository postItRepository;
    @Mock
    private PostItCategoryRepository postItCategoryRepository;
    @Mock
    private UserAccountRepository userAccountRepository;

    private static UserAccount userAccount;
    private static final String testEmail = "testEmail";
    private static final String testNickName = "testNickName";
    private static final String testPwd = "testPwd";
    private static PostIt postItEntity1;
    private static PostIt postItEntity2;
    private static PostItCategory postItCategoryEntity1;
    private static PostItCategory postItCategoryEntity2;


    @BeforeEach
    void init() {
        userAccount = UserAccount.of(testEmail, testNickName, testPwd);
        PostItCategory category = PostItCategory.of("test-postit-category", userAccount);
        postItEntity1 = PostIt.of("test1", userAccount);
        postItEntity1.setCreatedAt(LocalDateTime.now());
        postItEntity1.setPostICategory(category);
        postItEntity2 = PostIt.of("test1", userAccount);
        postItEntity2.setCreatedAt(LocalDateTime.now());
        postItEntity2.setPostICategory(category);
        postItCategoryEntity1 = PostItCategory.of("test-category1", userAccount);
        postItCategoryEntity2 = PostItCategory.of("test-category2", userAccount);
    }

    @DisplayName("포스트잇 등록 테스트 - 성공")
    @Test
    void givenPostItRegisterRequest_whenRegisterPostIt_thenReturnPostItDto() {
        //given
        given(postItRepository.save(any(PostIt.class))).willReturn(postItEntity1);

        //when
        var savedPostItDto = sut.registerPostIt(userAccount, "test1", false);

        //then
        verify(postItRepository).save(any(PostIt.class));
        assertEquals("test1", savedPostItDto.content());
    }

    @DisplayName("포스트잇 조회 테스트")
    @Test
    void givenUserAccount_whenFindPostIT_thenReturnPostItDtoList() {
        //given
        given(postItRepository.findAllByUserAccount_Email(userAccount.getEmail())).willReturn(List.of(postItEntity1, postItEntity2));

        //when
        var postItList = sut.getAllPostIt(UserAccountDto.from(userAccount));

        //then
        verify(postItRepository).findAllByUserAccount_Email(any());
        assertEquals(2, postItList.size());
    }

    @DisplayName("포스트잇 카테고리 조회 테스트")
    @Test
    void givenUserAccount_whenFindPostItCategory_thenReturnPostItCategoryDtoList() {
        //given
        PostItCategory postItCategory = PostItCategory.of("test-category", userAccount);
        given(postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(userAccount.getEmail())).willReturn(List.of(postItCategory));

        //when
        var postItCategoryList = sut.getAllPostItCategory(UserAccountDto.from(userAccount));

        //then
        verify(postItCategoryRepository).findAllByUserAccount_EmailAndDeletedFalse(any());
        assertEquals(1, postItCategoryList.size());
    }

    @DisplayName("포스트잇 업데이트 로직 테스트")
    @Test
    void givenPostItDeleteRequest_whenUpdatePostIt_thenReturnPostItDtoList() {
        //given
        given(postItRepository.saveAll(any())).willReturn(List.of(postItEntity1));
        given(userAccountRepository.findUserAccountByEmail(any())).willReturn(Optional.of(userAccount));
        given(postItCategoryRepository.findByUserAccountAndPostItCategoryClientId(any(), any())).willReturn(Optional.of(postItCategoryEntity1));

        //when
        var postItList = sut.updatePostIt(List.of(), List.of(PostItDto.from(postItEntity1)), UserAccountDto.from(userAccount));

        //then
        verify(postItCategoryRepository).saveAll(any());
        verify(postItRepository).saveAll(any());
        assertEquals(1, postItList.size());
    }

    @DisplayName("포스트잇 카테고리 업데이트 로직 테스트")
    @Test
    void givenPostItCategoryUpdateRequest_whenUpdatePostItCategory_thenUpdatePostItCategory() {
        //given
        PostItCategoryDto categoryDto1 = PostItCategoryDto.from(postItCategoryEntity1);
        PostItCategoryDto categoryDto2 = PostItCategoryDto.from(postItCategoryEntity2);
        PostItDto postItDto1 = PostItDto.of(postItEntity1.getPostItClientId(), categoryDto1, postItEntity1.getContent(), postItEntity1.getCreatedAt(), postItEntity1.isDone());
        PostItDto postItDto2 = PostItDto.of(postItEntity2.getPostItClientId(), categoryDto2, postItEntity2.getContent(), postItEntity2.getCreatedAt(), postItEntity2.isDone());

        given(userAccountRepository.findUserAccountByEmail(any())).willReturn(Optional.of(userAccount));
        given(postItCategoryRepository.findByUserAccountAndPostItCategoryClientId(userAccount, categoryDto1.postItCategoryClientId())).willReturn(Optional.of(postItCategoryEntity1));
        given(postItCategoryRepository.findByUserAccountAndPostItCategoryClientId(userAccount, categoryDto2.postItCategoryClientId())).willReturn(Optional.empty());
        given(postItRepository.findByUserAccountAndPostItClientId(userAccount, postItEntity1.getPostItClientId())).willReturn(Optional.of(postItEntity1));
        given(postItRepository.findByUserAccountAndPostItClientId(userAccount, postItEntity2.getPostItClientId())).willReturn(Optional.empty());
        given(postItCategoryRepository.findByUserAccountAndPostItCategoryClientId(userAccount, categoryDto1.postItCategoryClientId())).willReturn(Optional.of(postItCategoryEntity1));
        given(postItCategoryRepository.findByUserAccountAndPostItCategoryClientId(userAccount, categoryDto2.postItCategoryClientId())).willReturn(Optional.of(postItCategoryEntity2));

        //when
        sut.updatePostIt(List.of(categoryDto1, categoryDto2), List.of(postItDto1, postItDto2), UserAccountDto.from(userAccount));

        //then
        verify(postItRepository).saveAll(any());
        verify(postItCategoryRepository).saveAll(any());
    }

}