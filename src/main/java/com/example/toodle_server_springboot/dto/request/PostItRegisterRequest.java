package com.example.toodle_server_springboot.dto.request;

import com.example.toodle_server_springboot.dto.postit.PostItCategoryDto;
import com.example.toodle_server_springboot.dto.postit.PostItDto;

public record PostItRegisterRequest(
    PostItCategoryDto categoryDto,
    String content,
    boolean isDone
) {

    public PostItRegisterRequest of(PostItCategoryDto categoryDto, String content, boolean isDone) {
        return new PostItRegisterRequest(categoryDto, content, isDone);
    }

    public PostItDto toDto() {
        return PostItDto.of(categoryDto, content, isDone);
    }
}
