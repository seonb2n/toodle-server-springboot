package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.repository.PostItRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 포스트잇")
@ExtendWith(MockitoExtension.class)
class PostItServiceTest {

    @InjectMocks private PostItService sut;

    @Mock private PostItRepository postItRepository;

    @DisplayName("포스트잇 등록 테스트")
    @Test
    void givenPostItRegisterRequest_whenRegisterPostIt_thenReturnPostItDto() {

    }

    @DisplayName("포스트잇 조회 테스트")
    @Test
    void givenUserAccount_whenFindPostIT_thenReturnPostItDtoList() {
        //given


        //when

        //then

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