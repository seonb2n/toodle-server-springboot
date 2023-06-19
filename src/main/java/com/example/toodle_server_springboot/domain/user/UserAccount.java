package com.example.toodle_server_springboot.domain.user;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id")
    private UUID userId;

    @Setter
    @Column(name = "user_email")
    private String email;

    @Setter
    @Column(name = "user_nickname")
    private String nickname;

    @Setter
    @Column(name = "user_password")
    private String password;

    @Setter
    @Column(name = "user_is_tmp_pwd")
    private boolean isTmpPassword;

    protected UserAccount() {
    }

    private UserAccount(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.isTmpPassword = false;
    }

    public static UserAccount of(String email, String nickname, String password) {
        return new UserAccount(email, nickname, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccount that = (UserAccount) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
