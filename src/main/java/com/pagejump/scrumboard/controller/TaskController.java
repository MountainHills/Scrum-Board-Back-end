package com.pagejump.scrumboard.controller;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Viewing all tasks.
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // View task by Id.
    @GetMapping(path = "{taskId}")
    public Optional<Task> getAllTasks(@PathVariable("taskId") Long taskId) {
        return taskService.getTaskById(taskId);
    }

    // For inserting new tasks.
    @PostMapping
    public void createNewTask(@RequestBody Task task) {
        taskService.addNewTask(task);
    }

    // For deleting a task.
    @DeleteMapping(path = "delete/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    @PutMapping(path = "soft-delete/{taskId}")
    public void safeDeleteTask(@PathVariable("taskId") Long taskId) {
        taskService.softDeleteTask(taskId);
    }

    // For updating a task.
    @PutMapping(path = "{taskId}")
    public void updateTask(@PathVariable("taskId") Long taskId,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String progress) {
        taskService.updateTask(taskId, title, description, progress);
    }
}
