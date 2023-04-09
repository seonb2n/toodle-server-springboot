package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.repository.PostItCategoryRepository;
import com.example.toodle_server_springboot.repository.PostItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostItCategoryService {

    private final PostItCategoryRepository postItCategoryRepository;
    private final PostItRepository postItRepository;

    /**
     * 사용자가 보유한 모든 유효한 카테고리를 가져온다.
     * @param userAccount
     * @return
     */
    List<PostItCategoryDto> findAllPostIt(UserAccount userAccount) {
        var postItCategoryList = postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(userAccount.getEmail());
        return postItCategoryList.stream().map(PostItCategoryDto::from).toList();
    }

    /**
     * 카테고리를 soft delete 한다.
     * @param postItCategoryClientId
     */
    void deleteCategory(UUID postItCategoryClientId) {
        postItCategoryRepository.deleteByPostItCategoryClientId(postItCategoryClientId);
    }
}
