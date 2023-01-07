package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
