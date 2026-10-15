
package com.mohamedamine.taskmanager_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // GET - Afficher toutes les tâches
    public List<Map<String, Object>> findAll() {

        String sql = """
                SELECT id, title, description, status
                FROM tasks
                ORDER BY id
                """;

        return jdbcTemplate.queryForList(sql);
    }

    // POST - Ajouter une tâche
    public void create(String title, String description) {

        String sql = """
                INSERT INTO tasks (title, description, status)
                VALUES (?, ?, 'TODO')
                """;

        jdbcTemplate.update(sql, title, description);
    }

    // PUT - Modifier le statut
    public int updateStatus(int id, String status) {

        String sql = """
                UPDATE tasks
                SET status = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql, status, id);
    }

    // DELETE - Supprimer une tâche
    public int delete(int id) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
