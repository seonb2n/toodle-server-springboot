package com.example.toodle_server_springboot.domain.project.task.action;

import com.example.toodle_server_springboot.domain.BaseEntity;
import com.example.toodle_server_springboot.domain.project.task.Task;
import com.example.toodle_server_springboot.domain.user.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 행동의 단위
 * 해야할 일과 기한, 완료 여부로 구성되어 있다.
 */
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "tb_action")
public class Action extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "action_id")
    private UUID actionId;


    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false)
    private Task task;

    @Setter
    @Column(name = "action_content")
    private String content;

    @Setter
    @Column(name = "action_due_date")
    private LocalDateTime dueDate;

    @Setter
    @Column(name = "actions_is_done")
    private boolean isDone;


    protected Action() {}

    private Action(UserAccount userAccount, Task task, String content, LocalDateTime dueDate, boolean isDone) {
        this.userAccount = userAccount;
        this.task = task;
        this.content = content;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    public static Action of(UserAccount userAccount, Task task, String content, LocalDateTime dueDate, boolean isDone) {
        return new Action(userAccount, task, content,  dueDate, isDone);
    }

    public void update(String content, LocalDateTime dueDate, boolean isDone) {
        this.content = content;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return isDone == action.isDone && Objects.equals(content, action.content) && Objects.equals(dueDate, action.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, dueDate, isDone);
    }
}
