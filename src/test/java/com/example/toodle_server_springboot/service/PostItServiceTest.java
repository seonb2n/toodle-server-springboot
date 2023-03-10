package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.PostItDto;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.repository.PostItRepository;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("비즈니스 로직 - 포스트잇")
@ExtendWith(MockitoExtension.class)
class PostItServiceTest {

    @InjectMocks private PostItService sut;

    @Mock private PostItRepository postItRepository;
    @Mock private UserAccountRepository userAccountRepository;

    private static UserAccount userAccount;
    private static final String testEmail = "testEmail";
    private static final String testNickName = "testNickName";
    private static final String testPwd = "testPwd";

    @BeforeEach
    void init() {
        userAccount = UserAccount.of(testEmail, testNickName, testPwd);
    }

    @DisplayName("포스트잇 등록 테스트 - 성공")
    @Test
    void givenPostItRegisterRequest_whenRegisterPostIt_thenReturnPostItDto() {
        //given
        String content = "content";
        given(postItRepository.save(any(PostIt.class))).willReturn(PostIt.of(content, userAccount));

        //when
        var savedPostItDto = sut.registerPostIt(userAccount, content, false);

        //then
        verify(postItRepository).save(any(PostIt.class));
        assertEquals(content, savedPostItDto.content());
    }

    @DisplayName("포스트잇 조회 테스트")
    @Test
    void givenUserAccount_whenFindPostIT_thenReturnPostItDtoList() {
        //given
        PostIt testPostIt1 = PostIt.of("test1", userAccount);
        PostIt testPostIt2 = PostIt.of("test2", userAccount);
        given(postItRepository.findAllByUserAccount_Email(userAccount.getEmail())).willReturn(List.of(testPostIt1, testPostIt2));

        //when
        var postItList = sut.getAllPostIt(UserAccountDto.from(userAccount));

        //then
        verify(postItRepository).findAllByUserAccount_Email(any());
        assertEquals(2, postItList.size());
    }

    @DisplayName("포스트잇 업데이트 로직 테스트")
    @Test
    void givenPostItDeleteRequest_whenUpdatePostIt_thenReturnPostItDtoList() {
        //given
        PostIt testPostIt1 = PostIt.of("test1", userAccount);
        given(postItRepository.saveAll(List.of(testPostIt1))).willReturn(List.of(testPostIt1));
        given(userAccountRepository.findUserAccountByEmail(any())).willReturn(Optional.of(userAccount));
        //when
        var postItList = sut.updatePostIt(List.of(PostItDto.from(testPostIt1)) ,UserAccountDto.from(userAccount));

        //then
        verify(postItRepository).saveAll(any());
        assertEquals(1, postItList.size());
    }
}