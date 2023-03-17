package com.example.toodle_server_springboot.domain.postIt;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_postit")
public class PostIt extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "postit_id")
    private UUID postItId;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @JoinColumn(name = "postITCategoryId")
    @ManyToOne(optional = false)
    private PostICategory postICategory;

    @Setter
    @Column(name = "postit_content")
    private String content;


    @Setter
    @Column(name = "postit_is_done")
    private boolean isDone;

    protected PostIt() {}

    private PostIt(String content, UserAccount userAccount, boolean isDone) {
        this.content = content;
        this.userAccount = userAccount;
        this.isDone = isDone;
    }

    public static PostIt of(String content, UserAccount userAccount) {
        return new PostIt(content, userAccount, false);
    }

    public static PostIt of(String content, UserAccount userAccount, boolean isDone) {
        return new PostIt(content, userAccount, isDone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostIt postIt = (PostIt) o;
        return isDone == postIt.isDone && Objects.equals(postItId, postIt.postItId) && Objects.equals(content, postIt.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postItId, content, isDone);
    }
}
