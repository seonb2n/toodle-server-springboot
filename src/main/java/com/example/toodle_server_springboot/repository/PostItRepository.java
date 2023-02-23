package com.example.toodle_server_springboot.repository;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostItRepository extends JpaRepository<PostIt, UUID> {

    List<PostIt> findAllByUserAccount_Email(String userEmail);

    void deleteAllByUserAccount_Email(String userEmail);
}
