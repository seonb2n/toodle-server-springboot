package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findUserAccountByEmail(String email);

}
