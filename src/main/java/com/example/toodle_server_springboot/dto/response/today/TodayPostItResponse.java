package com.example.toodle_server_springboot.dto.response.today;

import com.example.toodle_server_springboot.dto.postit.PostItDto;

import java.util.List;

public record TodayPostItResponse(
        List<String> content
) {

    public static TodayPostItResponse of(
            List<PostItDto> postItDtoList
    ) {
        var postItContentList = postItDtoList.stream().map(PostItDto::content).toList();
        return new TodayPostItResponse(postItContentList);
    }

}
