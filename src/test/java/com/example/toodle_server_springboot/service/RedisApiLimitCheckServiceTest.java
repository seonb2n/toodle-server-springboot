package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

@DisplayName("보안 로직 - IP 별로 API 호출 제약 체크")
@ExtendWith(MockitoExtension.class)
@Import({RedisConfig.class})
class RedisApiLimitCheckServiceTest {

    @DisplayName("IP 의 API 요청 건수를 Redis Server 에 저장한다")
    @Test
    public void givenIP_whenRequestAPI_thenSaveApiRequestCount() throws Exception {
        //given

        //when


        //then
    }

    @DisplayName("IP 의 API 요청 건수가 일정 건수를 넘는지 확인한다.")
    @Test
    public void givenIP_whenRequestAPI_thenCheckAPIRequestCount() throws Exception {
        //given


        //when


        //then
    }

}