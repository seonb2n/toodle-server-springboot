package com.example.toodle_server_springboot.domain.postIt;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_postit_category")
public class PostItCategory extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "postit_category_id")
    private UUID postItCategoryId;


    @Setter
    @Column(name = "postit_category_client_id")
    private UUID postItCategoryClientId;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(name = "postit_category_title")
    private String title;

    @OneToMany
    @ToString.Exclude
    private Set<PostIt> postItSet;

    private boolean deleted;

    public void setPostItSet(Set<PostIt> postItSet) {
        this.postItSet = postItSet;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    protected PostItCategory() {
    }

    private PostItCategory(String title, UserAccount userAccount) {
        this.title = title;
        this.userAccount = userAccount;
        this.postItCategoryClientId = UUID.randomUUID();
        this.postItSet = new HashSet<>();
        this.deleted = false;
    }

    public static PostItCategory of(String title, UserAccount userAccount) {
        return new PostItCategory(title, userAccount);
    }

    public static PostItCategory of(UUID postITCategoryClientId, String title, UserAccount userAccount) {
        PostItCategory postItCategory = new PostItCategory(title, userAccount);
        postItCategory.setPostItCategoryClientId(postITCategoryClientId);
        return postItCategory;
    }

    public void addPostIt(PostIt postIt) {
        this.postItSet.add(postIt);
        postIt.setPostICategory(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostItCategory that = (PostItCategory) o;
        return Objects.equals(postItCategoryClientId, that.postItCategoryClientId) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postItCategoryClientId, title);
    }
}
