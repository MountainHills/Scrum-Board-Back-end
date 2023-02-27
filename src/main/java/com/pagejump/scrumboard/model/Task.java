package com.pagejump.scrumboard.model;

import com.pagejump.scrumboard.model.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Digits(message = "Id should be a whole number.", integer = 3, fraction = 2)
    @Id
    @SequenceGenerator(
            name = "task_seq",
            sequenceName = "task_seq",
            allocationSize = 1
    )
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "creationTime", updatable = false)
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "updateTime", nullable = false)
    private LocalDateTime updateTime;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
