package com.example.toodle_server_springboot.domain.project;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.project.task.Task;
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
@Table(name = "project")
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Task> taskSet = new LinkedHashSet<>();

    public void addTask(Task task) {
        taskSet.add(task);
    }

    protected Project() {}

    private Project (UserAccount userAccount, String projectName) {
        this.userAccount = userAccount;
        this.projectName = projectName;
    }

    public static Project of(UserAccount userAccount, String projectName) {
        return new Project(userAccount, projectName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(userAccount, project.userAccount) && Objects.equals(projectName, project.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAccount, projectName);
    }
}
