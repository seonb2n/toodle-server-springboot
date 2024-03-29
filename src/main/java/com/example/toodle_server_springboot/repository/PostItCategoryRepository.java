package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItCategoryRepository extends JpaRepository<PostItCategory, UUID> {

    List<PostItCategory> findAllByUserAccount_EmailAndDeletedFalse(String userEmail);

    Optional<PostItCategory> findByUserAccountAndTitle(UserAccount userAccount, String title);

    Optional<PostItCategory> findByUserAccountAndPostItCategoryClientId(UserAccount userAccount,
        UUID uuid);

    void deleteByPostItCategoryClientId(UUID clientID);
}
