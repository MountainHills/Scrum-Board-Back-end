package com.pagejump.scrumboard.service;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final EntityManager entityManager;


    // TODO: Send appropriate response message when there are no tasks.
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
    // TODO: Create exception handling if the task does not exist.
    public Optional<Task> getTaskById(long taskId) {
        return taskRepository.findById(taskId);
    }

    // Inserting new tasks
    public Task createTask(Task task) {
        // This method takes a JSON response body containing the needed information.
        return taskRepository.save(task);
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

    // For updating an existing task
    // TODO: Exception handling for misspelled ENUM value and Task not Existing.
    @Transactional
    public Task updateTask(Long taskId, Task update) {
        // Checks whether a task with the given id exists.
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new IllegalStateException("The task with the id = " + taskId + "doesn't exist.")
                );

        // Checks whether title change is not null, less than 0, and not equal to current title.
        if (update.getTitle() != null
                && update.getTitle().length() > 0
                && !Objects.equals(task.getTitle(), update.getTitle())) {
            task.setTitle(update.getTitle());
        }

        // Checks whether description change is not equal to current description.
        if (!Objects.equals(task.getDescription(), update.getDescription())) {
            task.setDescription(update.getDescription());
        }

        // Checks whether progress change is not null and must be within the choices.
        if (!Objects.equals(task.getStatus(), update.getStatus())) {
            task.setStatus(update.getStatus());
        }

        return task;
    }
}
