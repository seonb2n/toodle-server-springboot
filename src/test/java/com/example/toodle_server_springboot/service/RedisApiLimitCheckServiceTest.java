package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.config.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("보안 로직 - IP 별로 API 호출 제약 체크")
@ExtendWith(MockitoExtension.class)
@Import({RedisConfig.class, RedisApiLimitCheckService.class})
class RedisApiLimitCheckServiceTest {

    private final RedisApiLimitCheckService sut;
    private final String TEST_IP = "123.456.789.001";

    //todo 실제 REDIS 서버가 아니라 가상의 Redis 서버에서 테스트해야 함

    RedisApiLimitCheckServiceTest(@Autowired RedisApiLimitCheckService redisApiLimitCheckService) {
        this.sut = redisApiLimitCheckService;
    }

    @DisplayName("IP 정상 저장 및 호출 테스트")
    @Test
    public void givenIP_whenRequestAPI_thenSaveApiRequestCount() throws Exception {
        //given &when
        sut.setNewIP(TEST_IP);

        //then
        assertEquals(0, sut.getCount(TEST_IP));
    }

    @DisplayName("IP 저장 후 초기화 테스트")
    @Test
    public void givenIP_whenRemoveIP_thenReturnNothing() throws Exception {
        //given & when
        sut.setNewIP(TEST_IP);
        sut.clearIP(TEST_IP);

        //then
        assertEquals(0, sut.getCount(TEST_IP));
    }

    @DisplayName("IP 정상 저장 후, 카운트 증가 테스트")
    @Test
    public void givenIP_whenRequestAPI_thenCheckAPIRequestCount() throws Exception {
        //given & when
        sut.clearIP(TEST_IP);
        sut.setNewIP(TEST_IP);
        sut.addCount(TEST_IP);

        //then
        assertEquals(1, sut.getCount(TEST_IP));
    }

    @DisplayName("IP 가 오늘 호출 건수를 넘었는지 검증한다.")
    @Test
    public void givenIP_whenRequestAPI_thenValidIP() throws Exception {
        //given
        sut.clearIP(TEST_IP);
        sut.setNewIP(TEST_IP);
        for (int i = 0; i < 10; i++) {
            sut.addCount(TEST_IP);
        }

        //when & then
        assertFalse(sut.IPIsValid(TEST_IP));
    }
}