package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.log.UserRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRequestLogRepository extends JpaRepository<UserRequestLog, UUID> {

}
