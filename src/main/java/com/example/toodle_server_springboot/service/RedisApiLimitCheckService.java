package com.example.toodle_server_springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisApiLimitCheckService {

    private final RedisTemplate redisTemplate;

    private final int MAX_API_CALL_LIMIT_PER_DAY = 10;

    /**
     * 새로운 IP 를 redis 에 등록한다.
     * @param IP
     * @return
     */
    public int setNewIP(String IP) {
       return 0;
    }

    /**
     * 해당 IP 의 API 호출 건수를 가져온다.
     * @param ip
     * @return
     */
    public int getCount(String ip) {
        return 0;
    }

    /**
     * 해당 IP 의 API 호출 건수를 증가시킨다.
     * @param ip
     * @return
     */
    public int addCount(String ip) {
        return 0;
    }

    /**
     * 해당 IP 가 오늘 몇번 API 를 호출했는지 체크한다.
     * @param ip
     * @return
     */
    public int checkIP(String ip) {

        return 0;
    }

    /**
     * 해당 IP 의 API 호출 기록을 제거한다.
     * @param ip
     */
    public void clearIP(String ip) {

    }

    /**
     * API 를 호출할 수 있는 유효한 IP 인지 검사한다.
     * @param ip
     * @return
     */
    public boolean IPIsValid(String ip) {
        return checkIP(ip) > MAX_API_CALL_LIMIT_PER_DAY;
    }

}
