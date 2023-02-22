package com.pagejump.scrumboard.service;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.model.enums.TaskProgress;
import com.pagejump.scrumboard.model.enums.TaskState;
import com.pagejump.scrumboard.repository.TaskRepository;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Getting all tasks.
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Getting single task by ID.
    public Optional<Task> getTaskById(long taskId) {
        return taskRepository.findById(taskId);
    }

    // Inserting new tasks
    public void addNewTask(Task task) {
        // This method takes a JSON response body containing the needed information.
        taskRepository.save(task);
    }

    // Permanently deleting existing tasks
    public void deleteTask(long taskId) {
        boolean exists = taskRepository.existsById(taskId);

        // Checks whether a task with the given id exists.
        if (!exists) {
            throw new IllegalStateException("The task with the id = " + taskId + " doesn't exist.");
        }

        // Permanently deletes the record.
        taskRepository.deleteById(taskId);
    }

    // Safe deleting existing tasks
    @Transactional
    public void softDeleteTask(long taskId) {
        boolean exists = taskRepository.existsById(taskId);

        // Checks whether a task with the given id exists.
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new IllegalStateException("The task with the id = " + taskId + "doesn't exist.")
                );

        // Soft deletes the record.
        task.setState(TaskState.SOFT_DELETED);
    }

    // For updating an existing task
    @Transactional
    public void updateTask(Long taskId, String title, String description, String progress) {
        // Checks whether a task with the given id exists.
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new IllegalStateException("The task with the id = " + taskId + "doesn't exist.")
                );

        // Checks whether title change is not null, less than 0, and not equal to current title.
        if (title != null && title.length() > 0 && !Objects.equals(task.getTitle(), title)) {
            task.setTitle(title);
        }

        // Checks whether description change is not equal to current description.
        if (!Objects.equals(task.getDescription(), description)) {
            task.setDescription(description);
        }

        // Checks whether progress change is not null and must be within the choices.
        if ((progress != null)
                && (progress.length() > 0)
                && EnumUtils.isValidEnum(TaskProgress.class, progress.toUpperCase())) {
            task.setProgress(TaskProgress.valueOf(progress.toUpperCase()));
        }

    }
}
