
package com.mohamedamine.taskmanager_api;

import com.mohamedamine.taskmanager_api.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET
    @GetMapping("/api/tasks")
    public List<Map<String, Object>> getTasks() {
        return taskService.getAllTasks();
    }

    // POST
    @PostMapping("/api/tasks")
    public void createTask(@RequestBody Map<String, String> task) {
        taskService.createTask(
                task.get("title"),
                task.get("description")
        );
    }

    // PUT
    @PutMapping("/api/tasks/{id}")
    public void updateTask(
            @PathVariable int id,
            @RequestBody Map<String, String> task) {

        taskService.updateTask(id, task.get("status"));
    }

    // DELETE
    @DeleteMapping("/api/tasks/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
}
