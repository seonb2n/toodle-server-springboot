package com.example.toodle_server_springboot.config;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Mock Redis 객체 설정
 */
@TestConfiguration
public class TestRedisConfig {

    private final String TEST_IP = "123.456.789.001";
    private final String TEST_IP_HEADER = "LIMIT_IP_";

    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    private static ValueOperations<String, Object> valueOperationsMock;

    @BeforeEach
    void init() {
        valueOperationsMock = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        doNothing().when(valueOperationsMock).set(TEST_IP_HEADER + TEST_IP, 1, 60*60, TimeUnit.SECONDS);
        given(redisTemplate.opsForValue().get(any())).willReturn("0");
        given(redisTemplate.delete(TEST_IP_HEADER  +TEST_IP)).willReturn(true);
    }

}
