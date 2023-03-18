package com.example.toodle_server_springboot.dto.postit;

import com.example.toodle_server_springboot.domain.postIt.PostItCategory;
import com.example.toodle_server_springboot.domain.user.UserAccount;

import java.util.UUID;

public record PostItCategoryDto(
        UUID postItCategoryId,
        String title
) {

    public static PostItCategoryDto of(String title) {
        return new PostItCategoryDto(null, title);
    }

    public static PostItCategoryDto of(UUID postItCategoryId, String title) {
        return new PostItCategoryDto(postItCategoryId, title);
    }

    public static PostItCategoryDto from(PostItCategory postItCategory) {
        return PostItCategoryDto.of(
                postItCategory.getPostITCategoryId(),
                postItCategory.getTitle()
        );
    }

    public PostItCategory toEntity(UserAccount userAccount) {
        return PostItCategory.of(
                title,
                userAccount
        );
    }
}
