package com.pagejump.scrumboard.controller;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // For viewing
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // For inserting new tasks.
    @PostMapping
    public void createNewTask(@RequestBody Task task) {
        taskService.addNewTask(task);
    }

    // For deleting a task.
    @DeleteMapping(path = "{taskId}")
    public void deleteStudent(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    // For updating a task.
    @PutMapping(path = "{taskId}")
    public void updateStudent(@PathVariable("taskId") Long taskId,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String progress) {
        taskService.updateTask(taskId, title, description, progress);
    }
}
