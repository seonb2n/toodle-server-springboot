package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.repository.PostItRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("비즈니스 로직 - 포스트잇")
@ExtendWith(MockitoExtension.class)
class PostItServiceTest {

    @InjectMocks private PostItService sut;

    @Mock private PostItRepository postItRepository;

    private static UserAccount userAccount;
    private static String testEmail = "testEmail";
    private static String testNickName = "testNickName";
    private static String testPwd = "testPwd";

    @BeforeEach
    void init() {
        userAccount = UserAccount.of(testEmail, testNickName, testPwd);
    }

    @DisplayName("포스트잇 등록 테스트 - 성공")
    @Test
    void givenPostItRegisterRequest_whenRegisterPostIt_thenReturnPostItDto() {
        //given
        String content = "content";
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1L);
        given(postItRepository.save(any(PostIt.class))).willReturn(PostIt.of(content, userAccount, tomorrow));

        //when
        var savedPostItDto = sut.registerPostIt(userAccount, content, tomorrow, false);

        //then
        verify(postItRepository).save(any(PostIt.class));
        assertEquals(content, savedPostItDto.content());
    }

    @DisplayName("포스트잇 조회 테스트")
    @Test
    void givenUserAccount_whenFindPostIT_thenReturnPostItDtoList() {
        //given
        given(postItRepository.findAllByUserAccount(any(UserAccount.class))).willReturn(List.of());

        //when
        var postItList = sut.getAllPostIt(userAccount);

        //then
        verify(postItRepository).findAllByUserAccount(any(UserAccount.class));
    }

    @DisplayName("포스트잇 삭제 테스트")
    @Test
    void givenPostItDeleteRequest_whenDeletePostIt_thenReturnPostItDto() {
        //given


        //when

        //then

    }

    @DisplayName("포스트잇 완료 처리 테스트")
    @Test
    void givenPostItChagneReqeust_whenChangePostIt_thenReturnPostItDto() {
        //given


        //when

        //then

    }
}