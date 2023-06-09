package com.example.toodle_server_springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisApiLimitCheckService {

    private final RedisTemplate redisTemplate;

    /**
     * 새로운 IP 를 redis 에 등록한다.
     * @param IP
     * @return
     */
    private int setNewIP(String IP) {
       return 0;
    }

    /**
     * 해당 IP 가 오늘 api 접속 기록이 있는지 확인한다.
     * @param ip
     * @return
     */
    private boolean isNewIP(String ip) {
        return false;
    }

    /**
     * 해당 IP 가 오늘 몇번 API 를 호출했는지 체크한다.
     * @param ip
     * @return
     */
    public int checkIP(String ip) {

        return 0;
    }

}
