package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.config.RedisConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DisplayName("보안 로직 - IP 별로 API 호출 제약 체크")
@ExtendWith(MockitoExtension.class)
@Import({RedisConfig.class, RedisApiLimitCheckService.class})
class RedisApiLimitCheckServiceTest {

    @InjectMocks
    private RedisApiLimitCheckService sut;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    private static ValueOperations<String, Object> valueOperationsMock;

    private final String TEST_IP = "123.456.789.001";
    private final String TEST_IP_HEADER = "LIMIT_IP_";

    @BeforeEach
    void init() {
        valueOperationsMock = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
    }

    @DisplayName("IP 정상 저장 및 호출 테스트")
    @Test
    public void givenIP_whenRequestAPI_thenSaveApiRequestCount() throws Exception {
        //given
        doNothing().when(valueOperationsMock).set(TEST_IP_HEADER + TEST_IP, 1, 60*60, TimeUnit.SECONDS);
        given(redisTemplate.opsForValue().get(any())).willReturn("0");

        //when
        sut.setNewIP(TEST_IP);

        //then
        assertEquals(0, sut.getCount(TEST_IP));
    }

    @DisplayName("IP 저장 후 초기화 테스트")
    @Test
    public void givenIP_whenRemoveIP_thenReturnNothing() throws Exception {
        //given
        doNothing().when(valueOperationsMock).set(TEST_IP_HEADER + TEST_IP, 1, 60*60, TimeUnit.SECONDS);
        given(redisTemplate.delete(TEST_IP_HEADER  +TEST_IP)).willReturn(true);
        given(redisTemplate.opsForValue().get(any())).willReturn(null);

        //when
        sut.setNewIP(TEST_IP);
        sut.clearIP(TEST_IP);

        //then
        assertEquals(0, sut.getCount(TEST_IP));
    }

    @DisplayName("IP 정상 저장 후, 카운트 증가 테스트")
    @Test
    public void givenIP_whenRequestAPI_thenCheckAPIRequestCount() throws Exception {
        //given
        doNothing().when(valueOperationsMock).set(TEST_IP_HEADER + TEST_IP, 1, 60*60, TimeUnit.SECONDS);
        given(redisTemplate.opsForValue().get(any())).willReturn("1");
        doNothing().when(valueOperationsMock).set(TEST_IP_HEADER + TEST_IP, 2, 60*60, TimeUnit.SECONDS);

        // when
        sut.setNewIP(TEST_IP);
        sut.addCount(TEST_IP);

        //then
        assertEquals(1, sut.getCount(TEST_IP));
    }

    @DisplayName("IP 가 오늘 호출 건수를 넘었는지 검증한다.")
    @Test
    public void givenIP_whenRequestAPI_thenValidIP() throws Exception {
        //given
        doNothing().when(valueOperationsMock).set(TEST_IP_HEADER + TEST_IP, 1, 60*60, TimeUnit.SECONDS);
        given(redisTemplate.opsForValue().get(any())).willReturn("10");

        sut.setNewIP(TEST_IP);
        for (int i = 0; i < 10; i++) {
            sut.addCount(TEST_IP);
        }

        //when & then
        assertFalse(sut.IPIsValid(TEST_IP));
    }
}