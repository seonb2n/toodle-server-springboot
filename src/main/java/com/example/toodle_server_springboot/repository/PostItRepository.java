package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItRepository extends JpaRepository<PostIt, UUID> {

    List<PostIt> findAllByUserAccount_Email(String userEmail);

    List<PostIt> findAllByPostICategoryAndUserAccount_Email(PostItCategory postItCategory,
        String userEmail);

    Optional<PostIt> findByUserAccountAndPostItClientId(UserAccount userAccount, UUID uuid);

    void deleteAllByUserAccount_Email(String userEmail);
}
