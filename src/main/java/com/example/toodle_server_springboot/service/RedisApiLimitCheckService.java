package com.example.toodle_server_springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisApiLimitCheckService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final int MAX_API_CALL_LIMIT = 10;
    private final int EXPIRATION_DURATION = 60;
    private final String IP_HEADER = "LIMIT_IP_";

    /**
     * 새로운 IP 를 redis 에 등록한다.
     * @param ip
     * @return
     */
    public void setNewIP(String ip) {
        redisTemplate.opsForValue().set(IP_HEADER + ip, 1, EXPIRATION_DURATION, TimeUnit.SECONDS);
    }

    /**
     * 해당 IP 의 API 호출 건수를 가져온다.
     * @param ip
     * @return
     */
    public int getCount(String ip) {
        String ipCount = (String) redisTemplate.opsForValue().get(IP_HEADER  + ip);
        if (ipCount == null) {
            return 0;
        } else {
            return Integer.parseInt(ipCount);
        }
    }

    /**
     * 해당 IP 의 API 호출 건수를 증가시킨다.
     * @param ip
     * @return
     */
    public void addCount(String ip) {
        int ipCount = getCount(ip);
        if (ipCount == 0) {
            setNewIP(ip);
        }
        else {
            redisTemplate.opsForValue().set(IP_HEADER + ip, ipCount+1, EXPIRATION_DURATION, TimeUnit.SECONDS);
        }
    }

    /**
     * 해당 IP 가 오늘 몇번 API 를 호출했는지 체크한다.
     * @param ip
     * @return
     */
    public int checkIP(String ip) {
        addCount(ip);
        return getCount(ip);
    }

    /**
     * 해당 IP 의 API 호출 기록을 제거한다.
     * @param ip
     */
    public void clearIP(String ip) {
        redisTemplate.delete(IP_HEADER + ip);
    }

    /**
     * API 를 호출할 수 있는 유효한 IP 인지 검사한다.
     * @param ip
     * @return
     */
    public boolean IPisInvalid(String ip) {
        return checkIP(ip) > MAX_API_CALL_LIMIT;
    }

}
