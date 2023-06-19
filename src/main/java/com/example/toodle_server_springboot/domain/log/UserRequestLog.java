package com.example.toodle_server_springboot.domain.log;

import com.example.toodle_server_springboot.domain.BaseEntity;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Entity
@Getter
public class UserRequestLog extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "log_id")
    private UUID logId;

    private String userEmail;

    private String requestURL;

    @Enumerated(value = EnumType.STRING)
    private HttpMethod requestMethod;

    @Enumerated(value = EnumType.STRING)
    private HttpStatus responseCode;

    @Column(length = 4000)
    private String userRequestData;

    @Column(length = 4000)
    private String userResponseData;

    protected UserRequestLog() {
    }

    private UserRequestLog(String userEmail, String requestURL, HttpMethod requestMethod,
        HttpStatus responseCode, String userRequestData, String userResponseData) {
        this.userEmail = userEmail;
        this.requestURL = requestURL;
        this.requestMethod = requestMethod;
        this.responseCode = responseCode;
        this.userRequestData = userRequestData;
        this.userResponseData = userResponseData;
    }

    public static UserRequestLog of(String userEmail, String requestURL, HttpMethod requestMethod,
        HttpStatus responseCode, String userRequestData, String userResponseData) {
        return new UserRequestLog(userEmail, requestURL, requestMethod, responseCode,
            userRequestData, userResponseData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRequestLog that = (UserRequestLog) o;
        return Objects.equals(logId, that.logId) && Objects.equals(userEmail, that.userEmail)
            && Objects.equals(requestURL, that.requestURL) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, userEmail, requestURL, requestMethod);
    }
}
