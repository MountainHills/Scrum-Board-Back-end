package com.pagejump.scrumboard.dto;

import com.pagejump.scrumboard.dto.validation.ValidTaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskRequestDTO {
    @NotNull(message = "There should always be a Task Title.")
    @NotBlank(message = "There should always be a Task Title.")
    private String title;
    private String description;

    @ValidTaskStatus(message = "The Task Status is required. It can only be: TODO, IN_PROGRESS, FOR_REVIEW, DONE")
    private String status;
}