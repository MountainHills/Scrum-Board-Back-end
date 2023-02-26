package com.pagejump.scrumboard.controller;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Task> getAllTasks(@PathVariable("taskId") Long taskId) {
        log.info("Getting task with id = " + taskId);
        return taskService.getTaskById(taskId);
    }

    // For inserting new tasks.
    // TODO: Create an exception when updating a soft deleted task.
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("Inserting task with the following information: ");
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
                              @RequestBody Task update) {
        log.info("Updating task id #" + taskId + " with the following information: " + update.toString());
        return new ResponseEntity<>(taskService.updateTask(taskId, update), HttpStatus.ACCEPTED) ;
    }
}
