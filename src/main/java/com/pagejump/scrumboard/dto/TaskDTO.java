package com.pagejump.scrumboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private long id;
    @NotNull(message = "There should always be a Task title.")
    @NotBlank(message = "There should always be a Task title.")
    private String title;
    private String description;
    @NotNull(message = "There should always be a Task status.")
    @NotBlank(message = "There should always be a Task status.")
    // TODO: ENUM Custom Vlaidaotr.
    private String status;
}
