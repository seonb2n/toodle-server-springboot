package com.example.toodle_server_springboot.dto.response.today;

public record TodayPostItContentDto(
    String imgUrl,
    String content
) {

    public static TodayPostItContentDto of(String content) {
        return new TodayPostItContentDto("ic_quick_postit_ex.png", content);
    }
}
