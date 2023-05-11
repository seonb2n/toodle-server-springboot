package com.example.toodle_server_springboot.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionRepositoryRedisImpl implements SessionRepository {

    @Value("${spring.session.redis.namespace}")
    private String redisSessionNamespace;

    @Override
    public void saveSession(HttpSession session, String userId) {
        session.setAttribute(redisSessionNamespace, userId);
    }
}
