package com.example.toodle_server_springboot.domain.postIt;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_postit_category")
public class PostICategory extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "postit_category_id")
    private UUID postITCategoryId;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @OneToMany
    private Set<PostIt> postItSet;

    private String title;

    protected PostICategory() {}

    private PostICategory(String title, UserAccount userAccount) {
        this.title = title;
        this.userAccount = userAccount;
        this.postItSet = new LinkedHashSet<>();
    }

    public static PostICategory of(String title, UserAccount userAccount) {
        return new PostICategory(title, userAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostICategory that = (PostICategory) o;
        return Objects.equals(postITCategoryId, that.postITCategoryId) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postITCategoryId, title);
    }
}
