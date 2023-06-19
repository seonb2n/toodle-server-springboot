package com.example.toodle_server_springboot.dto.response;

import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;
import java.util.List;

public record PostItListPageResponse(
    List<PostItCategoryDto> postItCategoryDtoList,
    List<PostItDto> postItDtoList
) {

    public static PostItListPageResponse of(
        List<PostItCategoryDto> postItCategoryDtoList,
        List<PostItDto> postItDtoList
    ) {
        return new PostItListPageResponse(postItCategoryDtoList, postItDtoList);
    }
}
