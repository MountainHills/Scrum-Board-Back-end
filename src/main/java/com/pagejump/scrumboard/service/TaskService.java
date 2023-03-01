package com.pagejump.scrumboard.service;

import com.pagejump.scrumboard.dto.TaskRequestDTO;
import com.pagejump.scrumboard.exception.NoAvailableTaskListException;
import com.pagejump.scrumboard.exception.TaskDeletedException;
import com.pagejump.scrumboard.exception.TaskNotFoundException;
import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.model.enums.TaskStatus;
import com.pagejump.scrumboard.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;


    // Getting all tasks.
    public List<Task> getAllTasks(boolean isDeleted) {
        List<Task> tasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.isDeleted() == isDeleted)
                .toList();

        if (tasks.isEmpty()) throw new NoAvailableTaskListException(
                "There are no tasks to show where isDeleted = " + isDeleted);

        return tasks;
    }

    // Getting single task by ID.
    public Task getTaskById(long taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new TaskNotFoundException("The task with the id = " + taskId + " doesn't exist.")
                );
    }

    // Inserting new tasks
    public Task createTask(TaskRequestDTO taskRequestDto) {
        // This method takes a JSON response body containing the needed information.
        return taskRepository.save(
                new Task(taskRequestDto.getTitle(), taskRequestDto.getDescription())
        );
    }

    // Delete existing tasks
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new TaskNotFoundException("The task with the id = " + taskId + " doesn't exist.")
                );

        if (!task.isDeleted()) {
            taskRepository.deleteById(taskId);
        } else {
            throw new TaskDeletedException("Already deleted");
        }
    }

    // For updating an existing task
    @Transactional
    public Task updateTask(Long taskId, TaskRequestDTO update) {
        // Checks whether a task with the given id exists.
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new TaskNotFoundException("The task with the id = " + taskId + " doesn't exist.")
                );

        if (task.isDeleted())
            throw new TaskDeletedException("The task with the id = " + taskId + " is deleted. Can't update.");

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
            task.setStatus(TaskStatus.valueOf(update.getStatus().toUpperCase()));
        }

        return task;
    }
}
