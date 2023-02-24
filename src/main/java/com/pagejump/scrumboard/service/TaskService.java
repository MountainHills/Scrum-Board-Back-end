package com.pagejump.scrumboard.service;

import com.pagejump.scrumboard.exception.TaskNotFoundException;
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
    public Optional<Task> getTaskById(long taskId) {
        if (taskRepository.existsById(taskId)) {
            return taskRepository.findById(taskId);
        } else {
            throw new TaskNotFoundException("The task with the id = " + taskId + " doesn't exist.");
        }
    }

    // Inserting new tasks
    public Task createTask(Task task) {
        // This method takes a JSON response body containing the needed information.
        return taskRepository.save(task);
    }

    // Delete existing tasks
    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new TaskNotFoundException("The task with the id = " + taskId + " doesn't exist.");
        }
    }

    // For updating an existing task
    @Transactional
    public Task updateTask(Long taskId, Task update) {
        // Checks whether a task with the given id exists.
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new TaskNotFoundException("The task with the id = " + taskId + " doesn't exist.")
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
