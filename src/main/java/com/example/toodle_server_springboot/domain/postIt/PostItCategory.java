package com.example.toodle_server_springboot.domain.postIt;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_postit_category")
@SQLDelete(sql = "UPDATE tb_postit_category SET deleted = true where postit_category_id = ?")
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

    @Setter
    private boolean deleted = Boolean.FALSE;

    protected PostItCategory() {
    }

    private PostItCategory(String title, UserAccount userAccount) {
        this.title = title;
        this.userAccount = userAccount;
        this.postItCategoryClientId = UUID.randomUUID();
    }

    public static PostItCategory of(String title, UserAccount userAccount) {
        return new PostItCategory(title, userAccount);
    }

    public static PostItCategory of(UUID postITCategoryClientId, String title, UserAccount userAccount) {
        PostItCategory postItCategory = new PostItCategory(title, userAccount);
        postItCategory.setPostItCategoryClientId(postITCategoryClientId);
        return postItCategory;
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
