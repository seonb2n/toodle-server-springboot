package com.example.toodle_server_springboot.dto.request;

import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;

import java.util.List;

public record PostItUpdateRequest(
        List<PostItCategoryDto> postItCategoryDtoList,
        List<PostItDto> postItDtoList
) {

    public static PostItUpdateRequest of(
            List<PostItCategoryDto> postItCategoryDtoList,
            List<PostItDto> postItDtoList) {
        return new PostItUpdateRequest(postItCategoryDtoList, postItDtoList);
    }

    public List<PostItDto> getPostItDtoList() {
        return postItDtoList;
    }
}
