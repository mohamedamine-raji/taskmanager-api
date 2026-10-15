
package com.mohamedamine.taskmanager_api.service;

import com.mohamedamine.taskmanager_api.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // GET
    public List<Map<String, Object>> getAllTasks() {
        return taskRepository.findAll();
    }

    // POST
    public void createTask(String title, String description) {

        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title cannot be empty"
            );
        }

        taskRepository.create(title, description);
    }

    // PUT
    public void updateTask(int id, String status) {

        if (status == null ||
                !List.of("TODO", "IN_PROGRESS", "DONE").contains(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid task status"
            );
        }

        int updated = taskRepository.updateStatus(id, status);

        if (updated == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
    }

    // DELETE
    public void deleteTask(int id) {

        int deleted = taskRepository.delete(id);

        if (deleted == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
    }
}
