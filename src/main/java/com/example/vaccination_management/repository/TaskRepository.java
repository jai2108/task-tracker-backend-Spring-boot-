package com.example.vaccination_management.repository;


import com.example.vaccination_management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Basic CRUD methods are automatically handled by Spring Data JPA
}