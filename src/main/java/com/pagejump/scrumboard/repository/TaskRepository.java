package com.pagejump.scrumboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pagejump.scrumboard.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.deleted = true and t.id = ?1")
    Optional<Task> safeDeletedById(Long id);

}
