package com.example.toodle_server_springboot.domain.project.task;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.project.Project;
import com.example.toodle_server_springboot.domain.project.task.action.Action;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

/**
 * 업무의 최소 단위 여러 Action 으로 구성되어 있다.
 */
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_task", indexes = {
    @Index(name = "idx_task_id", columnList = "task_id")
})
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "task_id")
    private UUID taskId;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @JoinColumn(name = "projectId")
    @ManyToOne(optional = false)
    private Project project;

    private String content;

    @Column(name = "task_importance")
    @Enumerated(value = EnumType.STRING)
    private IMPORTNACE importnace;

    @Column(name = "task_start_at")
    private LocalDateTime startAt;

    @Column(name = "task_end_at")
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Action> actionSet = new LinkedHashSet<>();

    public void addAction(Action action) {
        actionSet.add(action);
    }

    protected Task() {
    }

    private Task(UserAccount userAccount, Project project, String content, IMPORTNACE importnace,
        LocalDateTime startAt, LocalDateTime endAt) {
        this.userAccount = userAccount;
        this.project = project;
        this.content = content;
        this.importnace = importnace;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static Task of(UserAccount userAccount, Project project, String content,
        IMPORTNACE importnace) {
        return new Task(userAccount, project, content, importnace, null, null);
    }

    public static Task of(UserAccount userAccount, Project project, String content,
        IMPORTNACE importnace, LocalDateTime startAt, LocalDateTime endAt) {
        return new Task(userAccount, project, content, importnace, startAt, endAt);
    }

    public void update(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(userAccount, task.userAccount) && Objects.equals(project,
            task.project) && Objects.equals(content, task.content) && Objects.equals(actionSet,
            task.actionSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAccount, project, content, actionSet);
    }

    public enum IMPORTNACE {
        HIGH,
        MIDDLE,
        LOW
    }
}
