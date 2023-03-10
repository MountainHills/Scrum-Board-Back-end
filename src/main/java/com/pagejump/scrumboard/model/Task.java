package com.pagejump.scrumboard.model;

import com.pagejump.scrumboard.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
// Annotations for Soft Deletion.
@SQLDelete(sql = "UPDATE task SET deleted = true WHERE id = ?")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
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
