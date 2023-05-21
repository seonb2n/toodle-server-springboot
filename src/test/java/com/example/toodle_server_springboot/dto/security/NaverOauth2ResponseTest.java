package com.example.toodle_server_springboot.dto.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTO - Naver Oauth 2.0 인증 응답 데이터 테스트")
class NaverOauth2ResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @DisplayName("인증 결과를 JSON 으로 받으면 네이버 응답 객체로 변환한다")
    @Test
    public void givenNaverLoginResultMapJson_whenInstantiating_thenReturnNaverResponseObject() throws Exception {
        //given
        String serializedResponse = """
                {
                  "resultcode": "200",
                  "message": "success",
                  "response": {
                    "email": "openapi@naver.com",
                    "nickname": "OpenAPI",
                    "age": "40-49",
                    "gender": "F",
                    "name": "오픈 API"
                  }
                }
                """;
        Map<String, Object> attributes = objectMapper.readValue(serializedResponse, new TypeReference<Map<String, Object>>() {
        });

        //when
        NaverOauth2Response result = NaverOauth2Response.from(attributes);

        //then
        assertThat(result)
                .hasFieldOrPropertyWithValue("resultCode", "200")
                .hasFieldOrPropertyWithValue("message", "success")
                .hasFieldOrPropertyWithValue("naverAccount.email", "openapi@naver.com")
                .hasFieldOrPropertyWithValue("naverAccount.nickName", "OpenAPI")
                ;
    }
}