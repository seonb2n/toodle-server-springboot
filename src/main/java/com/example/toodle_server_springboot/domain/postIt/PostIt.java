package com.example.toodle_server_springboot.domain.postIt;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_postit")
public class PostIt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(name = "postit_content")
    private String content;

    @Setter
    @Column(name = "postit_end_time")
    private LocalDateTime endTime;

    @Setter
    @Column(name = "postit_is_done")
    private boolean isDone;

    protected PostIt() {}

    private PostIt(String content, UserAccount userAccount, LocalDateTime endTime, boolean isDone) {
        this.content = content;
        this.userAccount = userAccount;
        this.endTime = endTime;
        this.isDone = isDone;
    }

    public static PostIt of(String content, UserAccount userAccount, LocalDateTime endTime) {
        return new PostIt(content, userAccount, endTime, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostIt postIt = (PostIt) o;
        return isDone == postIt.isDone && Objects.equals(id, postIt.id) && Objects.equals(content, postIt.content) && Objects.equals(endTime, postIt.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, endTime, isDone);
    }
}
