package com.pagejump.scrumboard.service;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.model.enums.TaskProgress;
import com.pagejump.scrumboard.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EntityManager entityManager;

    @Autowired
    public TaskService(TaskRepository taskRepository, EntityManager entityManager) {
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
    }

    // Getting all tasks.
    public List<Task> getAllTasks(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedTaskFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Task> tasks = taskRepository.findAll();
        session.disableFilter("deletedTaskFilter");
        return tasks;
    }

    // Getting single task by ID.
    // TODO: Ask Ma'am Jane if the API is only able to get active tasks? Meaning are we going to disregard soft deleted tasks?
    public Optional<Task> getTaskById(long taskId) {
        return taskRepository.findById(taskId);
    }

    // TODO: After creating the task, return the task object or the task id.
    // Inserting new tasks
    public void createTask(Task task) {
        // This method takes a JSON response body containing the needed information.
        taskRepository.save(task);
    }

    // Delete existing tasks
    public void deleteTask(Long taskId) {
        boolean exists = taskRepository.existsById(taskId);

        // Checks whether a task with the given id exists.
        if (!exists) {
            throw new IllegalStateException("The task with the id = " + taskId + " doesn't exist.");
        }

        // Permanently deletes the record.
        taskRepository.deleteById(taskId);
    }

    // TODO: Update needs to accommodate for JSON values instead of request parameters.
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
            task.setStatus(TaskProgress.valueOf(progress.toUpperCase()));
        }

    }
}
