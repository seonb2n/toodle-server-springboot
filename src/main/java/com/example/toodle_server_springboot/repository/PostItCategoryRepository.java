package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostItCategoryRepository extends JpaRepository<PostItCategory, UUID> {

    List<PostItCategory> findAllByUserAccount_EmailAndDeletedFalse(String userEmail);

}
