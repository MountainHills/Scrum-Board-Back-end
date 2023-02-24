package com.pagejump.scrumboard.controller;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    // Viewing all tasks.
    @GetMapping
    public List<Task> getAllTasks(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        log.info("The value of is deleted is: " + isDeleted);
        return taskService.getAllTasks(isDeleted);
    }

    // View active task by ID.
    @GetMapping(path = "{taskId}")
    public Optional<Task> getAllTasks(@PathVariable("taskId") Long taskId) {
        return taskService.getTaskById(taskId);
    }

    // For inserting new tasks.
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    // For updating a task.
    @PutMapping(path = "{taskId}")
    public Task updateTask(@PathVariable("taskId") Long taskId,
                              @RequestBody Task update) {
        return taskService.updateTask(taskId, update);
    }
}
