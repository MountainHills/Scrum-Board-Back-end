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
    public List<Task> getAllTasks(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        return taskService.getAllTasks(isDeleted);
    }

    // View active task by ID.
    @GetMapping(path = "{taskId}")
    public Optional<Task> getAllTasks(@PathVariable("taskId") Long taskId) {
        return taskService.getTaskById(taskId);
    }

    // For inserting new tasks.
    @PostMapping
    public void createTask(@RequestBody Task task) {
        taskService.createTask(task);
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    // For updating a task.
//    @PutMapping(path = "{taskId}")
//    public void updateTask(@PathVariable("taskId") Long taskId,
//                              @RequestParam(required = false) String title,
//                              @RequestParam(required = false) String description,
//                              @RequestParam(required = false) String progress) {
//        taskService.updateTask(taskId, title, description, progress);
//    }
}
