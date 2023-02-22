package com.pagejump.scrumboard.config;

import com.pagejump.scrumboard.model.Task;
import com.pagejump.scrumboard.model.enums.TaskProgress;
import com.pagejump.scrumboard.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class TaskConfig {
    @Bean
    CommandLineRunner commandLineRunner(TaskRepository repository) {
        return args -> {

            // Mock Data
            Task task1 = new Task("Do dishes", "Wash dishes for fun", TaskProgress.IN_PROGRESS);
            Task task2 = new Task("Practice Spring Boot", "What is Liquibase?", TaskProgress.DONE);
            Task task3 = new Task("Cook food", "I don't know", TaskProgress.TODO);
            Task task4 = new Task("Liquibase", "What the heck", TaskProgress.FOR_REVIEW);

            repository.saveAll(
                    List.of(task1, task2, task3, task4)
            );
        };
    }
}
