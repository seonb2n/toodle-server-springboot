package com.example.toodle_server_springboot.domain.project;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.project.task.Task;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_project", indexes = {
    @Index(name = "idx_project_id", columnList = "project_id")
})
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "project_id")
    private UUID projectId;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    private String projectName;

    private String projectColor;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Task> taskSet = new LinkedHashSet<>();

    public void addTask(Task task) {
        taskSet.add(task);
    }

    protected Project() {
    }

    private Project(UserAccount userAccount, String projectName, String projectColor) {
        this.userAccount = userAccount;
        this.projectName = projectName;
        this.projectColor = projectColor;
    }

    public static Project of(UserAccount userAccount, String projectName, String projectColor) {
        return new Project(userAccount, projectName, projectColor);
    }

    public void update(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(userAccount, project.userAccount) && Objects.equals(projectName,
            project.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAccount, projectName);
    }
}
