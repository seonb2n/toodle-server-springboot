package com.example.toodle_server_springboot.service;

import com.example.toodle_server_springboot.domain.postIt.PostIt;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.PostItDto;
import com.example.toodle_server_springboot.dto.UserAccountDto;
import com.example.toodle_server_springboot.repository.PostItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostItService {

    private final PostItRepository postItRepository;

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
        var postItList = postItDtoLIst.stream().map(it -> it.toEntity(userAccountDto.toEntity())).toList();
        //todo 서버의 postIt과 비교해서 삭제된 postIt 은 삭제처리를 해줘야 한다.
        return postItRepository.saveAll(postItList).stream().map(PostItDto::from).toList();
    }
}
