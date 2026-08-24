package com.example.vaccination_management.controller;


import com.example.vaccination_management.model.Task;
import com.example.vaccination_management.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // Allows React frontend to communicate without CORS issues
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // 1. READ ALL (GET /tasks) -> Connected to React useEffect
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 2. CREATE (POST /tasks) -> Connected to React handleSubmitTask
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    // 3. UPDATE / EDIT (PATCH /tasks/{id}) -> Connected to React Edit flow
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTaskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task existingTask = optionalTask.get();

        if (updatedTaskDetails.getTitle() != null) {
            existingTask.setTitle(updatedTaskDetails.getTitle());
        }
        if (updatedTaskDetails.getDescription() != null) {
            existingTask.setDescription(updatedTaskDetails.getDescription());
        }

        Task savedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(savedTask);
    }

    // 4. DELETE (DELETE /tasks/{id}) -> Connected to React handleDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}