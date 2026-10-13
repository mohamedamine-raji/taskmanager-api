
package com.mohamedamine.taskmanager_api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    private final JdbcTemplate jdbcTemplate;

    public TaskController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. GET - Afficher toutes les tâches
    @GetMapping("/api/tasks")
    public List<Map<String, Object>> getTasks() {

        String sql = """
                SELECT id, title, description, status
                FROM tasks
                ORDER BY id
                """;

        return jdbcTemplate.queryForList(sql);
    }

    // 2. POST - Ajouter une tâche
    @PostMapping("/api/tasks")
    public void createTask(@RequestBody Map<String, String> task) {

        String title = task.get("title");
        String description = task.get("description");

        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title cannot be empty"
            );
        }

        String sql = """
                INSERT INTO tasks (title, description, status)
                VALUES (?, ?, 'TODO')
                """;

        jdbcTemplate.update(sql, title, description);
    }

    // 3. PUT - Modifier le statut d'une tâche
    @PutMapping("/api/tasks/{id}")
    public void updateTask(
            @PathVariable int id,
            @RequestBody Map<String, String> task) {

        String status = task.get("status");

        if (status == null ||
                !List.of("TODO", "IN_PROGRESS", "DONE").contains(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid task status"
            );
        }

        String sql = """
                UPDATE tasks
                SET status = ?
                WHERE id = ?
                """;

        int updated = jdbcTemplate.update(sql, status, id);

        if (updated == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
    }

    // 4. DELETE - Supprimer une tâche
    @DeleteMapping("/api/tasks/{id}")
    public void deleteTask(@PathVariable int id) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        int deleted = jdbcTemplate.update(sql, id);

        if (deleted == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
    }
}
