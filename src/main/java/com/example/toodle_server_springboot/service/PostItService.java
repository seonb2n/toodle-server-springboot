package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;
import com.example.toodle_server_springboot.repository.PostItCategoryRepository;
import com.example.toodle_server_springboot.repository.PostItRepository;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostItService {

    private final PostItRepository postItRepository;
    private final PostItCategoryRepository postItCategoryRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * PostIt 저장하는 메서드
     * @param userAccount
     * @param content
     * @param isDone
     * @return
     */
    public PostItDto registerPostIt(UserAccount userAccount, String content, boolean isDone) {
        var initPostItEntity = PostIt.of(content, userAccount, isDone);
        return PostItDto.from(postItRepository.save(initPostItEntity));
    }

    /**
     * 사용자 계정이 가진 모든 PostIt 을 찾는 서비스
     * @param userAccountDto
     * @return
     */
    @Transactional(readOnly = true)
    public List<PostItDto> getAllPostIt(UserAccountDto userAccountDto) {
        return postItRepository.findAllByUserAccount_Email(userAccountDto.email()).stream().map(PostItDto::from).toList();
    }

    /**
     * 사용자 계정이 가진 삭제되지 않은 PostItCategory 를 찾는 서비스
     * @param userAccountDto
     * @return
     */
    @Transactional(readOnly = true)
    public List<PostItCategoryDto> getAllPostItCategory(UserAccountDto userAccountDto) {
        return postItCategoryRepository.findAllByUserAccount_EmailAndDeletedFalse(userAccountDto.email()).stream().map(PostItCategoryDto::from).toList();
    }

    /**
     * 수정된 포스트잇 리스트로 업데이트
     * @param postItCategoryDtoList
     * @param postItDtoLIst
     * @param userAccountDto
     * @return
     */
    public List<PostItDto> updatePostIt(
            List<PostItCategoryDto> postItCategoryDtoList,
            List<PostItDto> postItDtoLIst,
            UserAccountDto userAccountDto) {
        var userAccount = userAccountRepository.findUserAccountByEmail(userAccountDto.email()).orElseThrow();
        // 카테고리 생성
        var updatedCategoryList = postItCategoryDtoList.stream().map(it ->
                postItCategoryRepository.findById(it.postItCategoryId())
                .orElse(PostItCategory.of(it.title(), userAccount))).toList();
        postItCategoryRepository.saveAll(updatedCategoryList);

        // 포스트잇 생성
        var updatePostItList = postItDtoLIst.stream().map(it -> {
            var postItTitle = it.categoryDto().title();
            //todo 새로운 카테고리를 할당할 때, title 로 검색되는 것은 안전하지 않다.
            var category = postItCategoryRepository.findByUserAccountAndTitle(userAccount, postItTitle).orElseThrow();
            return postItRepository.findById(it.postItId())
                    .orElse(PostIt.of(it.content(), category, userAccount));
        }).toList();
        return postItRepository.saveAll(updatePostItList).stream().map(PostItDto::from).toList();
    }
}
