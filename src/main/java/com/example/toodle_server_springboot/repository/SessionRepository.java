package com.example.toodle_server_springboot.repository;

import javax.servlet.http.HttpSession;

public interface SessionRepository {

    void saveSession(HttpSession session, String userId);

}
