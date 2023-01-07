package com.example.toodle_server_springboot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class UserAccount extends BaseEntity{

    @Id
    private String userId;

    @Setter private String email;
    @Setter private String nickname;
    @Setter private String password;

    protected UserAccount() {}

    private UserAccount(String userId, String email, String nickname, String password) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static UserAccount of(String userId, String email, String nickname, String password) {
        return new UserAccount(userId, email, nickname, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
