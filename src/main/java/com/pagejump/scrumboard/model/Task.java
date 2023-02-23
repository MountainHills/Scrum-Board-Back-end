package com.pagejump.scrumboard.model;

import com.pagejump.scrumboard.model.enums.TaskProgress;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "task")
// Annotations for Soft Deletion.
@SQLDelete(sql = "UPDATE task SET deleted = true WHERE id = ?")
// Annotations for displaying soft deleted tasks.
@FilterDef(
        name = "deletedTaskFilter",
        parameters = @ParamDef(name = "isDeleted", type = Boolean.class)
)
@Filter(
        name = "deletedTaskFilter",
        condition = "deleted = :isDeleted"
)
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_seq",
            sequenceName = "task_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_seq"
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private TaskProgress status = TaskProgress.TODO;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = Boolean.FALSE;

    // TODO: Add attributes CreationTimestamp and UpdateTimestamp.

    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskProgress getStatus() {
        return status;
    }

    public void setStatus(TaskProgress status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
