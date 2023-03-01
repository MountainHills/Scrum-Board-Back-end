package com.pagejump.scrumboard.controller;

import com.pagejump.scrumboard.dto.TaskRequestDTO;
import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    // Viewing all tasks depending on isDeleted parameter.
    @GetMapping
    public List<Task> getAllTasks(
            @RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        log.info("Getting all tasks where deleted = " + isDeleted);
        return taskService.getAllTasks(isDeleted);
    }

    // View task by ID.
    @GetMapping(path = "{taskId}")
    public Task getTaskById(@PathVariable("taskId") Long taskId) {
        log.info("Getting task with id = " + taskId);
        return taskService.getTaskById(taskId);
    }

    // For inserting new tasks.
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskRequestDTO task) {
        log.info("Inserting task with the following information: title = " + task.getTitle()
        + ", description = " + task.getDescription());
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable("taskId") Long taskId) {
        log.info("Soft deleting task with id = " + taskId);
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // For updating a task.
    @PutMapping(path = "{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") Long taskId,
                              @RequestBody @Valid TaskRequestDTO update) {
        log.info("Updating task id #" + taskId + " with the following information: " + update.toString());
        return new ResponseEntity<>(taskService.updateTask(taskId, update), HttpStatus.ACCEPTED);

    }
}
