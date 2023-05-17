package com.example.toodle_server_springboot.domain.postIt;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import com.example.toodle_server_springboot.dto.postit.PostItDto;
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
    @Column(name = "postit_client_id")
    private UUID postItClientId;


    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @JoinColumn(name = "postit_category_id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private PostItCategory postICategory;

    @Setter
    @Column(name = "postit_content")
    private String content;


    @Setter
    @Column(name = "postit_is_done")
    private boolean isDone;

    protected PostIt() {
    }

    private PostIt(String content, UserAccount userAccount, boolean isDone) {
        this.content = content;
        this.userAccount = userAccount;
        this.isDone = isDone;
        this.postItClientId = UUID.randomUUID();
    }

    public static PostIt of(String content, UserAccount userAccount) {
        return new PostIt(content, userAccount, false);
    }

    public static PostIt of(String content, UserAccount userAccount, boolean isDone) {
        return new PostIt(content, userAccount, isDone);
    }

    public static PostIt of(String content, PostItCategory postItCategory, UserAccount userAccount) {
        PostIt postIt = new PostIt(content, userAccount, false);
        postIt.setPostICategory(postItCategory);
        return postIt;
    }

    public static PostIt of(UUID postItClientId, String content, PostItCategory postItCategory, UserAccount userAccount, boolean isDone) {
        PostIt postIt = new PostIt(content, userAccount, isDone);
        postIt.setPostICategory(postItCategory);
        postIt.setPostItClientId(postItClientId);
        return postIt;
    }

    /**
     * PostItDto 와 비교해서 내용을 업데이트한다.
     * @param postItDto
     */
    public void update(PostItDto postItDto) {
        if (this.postItClientId.equals(postItDto.postItClientId())) {
            this.isDone = postItDto.isDone();
        }
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
