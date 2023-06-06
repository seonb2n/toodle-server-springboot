package com.example.toodle_server_springboot.domain.log;

import com.example.toodle_server_springboot.domain.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
public class UserRequestLog extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "log_id")
    private UUID logId;

    private UUID userId;

    private String requestURL;

    @Enumerated(value = EnumType.STRING)
    private HttpMethod requestMethod;

    @Enumerated(value = EnumType.STRING)
    private HttpStatus responseCode;

    private String userRequestData;

    private String userResponseData;

    protected UserRequestLog() {
    }

    private UserRequestLog(UUID userId, String requestURL, HttpMethod requestMethod, HttpStatus responseCode, String userRequestData, String userResponseData) {
        this.userId = userId;
        this.requestURL = requestURL;
        this.requestMethod = requestMethod;
        this.responseCode = responseCode;
        this.userRequestData = userRequestData;
        this.userResponseData = userResponseData;
    }

    public static UserRequestLog of(UUID userId, String requestURL, HttpMethod requestMethod, HttpStatus responseCode, String userRequestData, String userResponseData) {
        return new UserRequestLog(userId, requestURL, requestMethod, responseCode, userRequestData, userResponseData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequestLog that = (UserRequestLog) o;
        return Objects.equals(logId, that.logId) && Objects.equals(userId, that.userId) && Objects.equals(requestURL, that.requestURL) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, userId, requestURL, requestMethod);
    }
}
