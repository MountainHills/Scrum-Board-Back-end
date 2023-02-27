package com.pagejump.scrumboard.mapper;

import com.pagejump.scrumboard.dto.TaskDTO;
import com.pagejump.scrumboard.model.Task;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TaskDTOMapper implements Function<Task, TaskDTO> {

    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString()
        );
    }
}
