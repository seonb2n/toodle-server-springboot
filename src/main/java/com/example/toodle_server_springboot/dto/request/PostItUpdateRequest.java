package com.example.toodle_server_springboot.dto.request;

import com.example.toodle_server_springboot.dto.PostItDto;

import java.util.List;

public record PostItUpdateRequest(
        List<PostItDto> postItDtoList
) {

    public PostItUpdateRequest of(List<PostItDto> postItDtoList) {
        return new PostItUpdateRequest(postItDtoList);
    }

    public List<PostItDto> getPostItDtoList() {
        return postItDtoList;
    }
}
