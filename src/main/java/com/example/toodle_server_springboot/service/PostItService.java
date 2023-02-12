package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.PostItDto;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.repository.PostItRepository;
import com.example.toodle_server_springboot.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostItService {

    private final PostItRepository postItRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * PostIt 저장하는 메서드
     * @param userAccount
     * @param content
     * @param endTime
     * @param isDone
     * @return
     */
    public PostItDto registerPostIt(UserAccount userAccount, String content, LocalDateTime endTime, boolean isDone) {
        var initPostItEntity = PostIt.of(content, userAccount, endTime, isDone);
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
     * 수정된 포스트잇 리스트로 업데이트
     * @param postItDtoLIst
     * @param userAccountDto
     * @return
     */
    public List<PostItDto> updatePostIt(List<PostItDto> postItDtoLIst, UserAccountDto userAccountDto) {
        // 원래 갖고 있던 포스트잇을 삭제한다
        // todo soft delete 와 hard delete 중 뭐가 더 좋을까?
        postItRepository.deleteAllByUserAccount_Email(userAccountDto.email());
        // 새로운 포스트잇 리스트로 업데이트한다
        var userAccount = userAccountRepository.findUserAccountByEmail(userAccountDto.email()).orElseThrow();
        var updatePostItList = postItDtoLIst.stream().map(it -> it.toEntity(userAccount)).toList();
        return postItRepository.saveAll(updatePostItList).stream().map(PostItDto::from).toList();
    }
}
