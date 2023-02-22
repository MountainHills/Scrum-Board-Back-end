package com.pagejump.scrumboard.model;

import com.pagejump.scrumboard.model.enums.TaskProgress;
import com.pagejump.scrumboard.model.enums.TaskState;
import jakarta.persistence.*;

@Entity
@Table(name = "task")
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
    @Column(name = "progress", length = 20, nullable = false)
    private TaskProgress progress;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 20, nullable = false)
    private TaskState state;


    public Task() {
    }

    public Task(String title, String description, TaskProgress progress) {
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.state = TaskState.valueOf("ACTIVE");
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

    public TaskProgress getProgress() {
        return progress;
    }

    public void setProgress(TaskProgress progress) {
        this.progress = progress;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}
