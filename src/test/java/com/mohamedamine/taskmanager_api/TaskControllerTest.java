
package com.mohamedamine.taskmanager_api;

import com.mohamedamine.taskmanager_api.repository.TaskRepository;
import com.mohamedamine.taskmanager_api.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskService.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository taskRepository;

    // TEST 1 - POST sans titre
    @Test
    void shouldRejectTaskWithoutTitle() throws Exception {

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "description": "Task without title"
                        }
                        """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(taskRepository);
    }

    // TEST 2 - POST avec titre
    @Test
    void shouldCreateTaskWithValidTitle() throws Exception {

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "Learn Testing",
                            "description": "Practice API tests"
                        }
                        """))
                .andExpect(status().isOk());

        verify(taskRepository).create(
                "Learn Testing",
                "Practice API tests"
        );
    }

    // TEST 3 - GET
    @Test
    void shouldGetAllTasks() throws Exception {

        when(taskRepository.findAll())
                .thenReturn(List.of(
                        Map.of(
                                "id", 1,
                                "title", "Learn Java",
                                "description", "Practice OOP",
                                "status", "DONE"
                        )
                ));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Learn Java"))
                .andExpect(jsonPath("$[0].status").value("DONE"));
    }

    // TEST 4 - PUT
    @Test
    void shouldUpdateTaskStatus() throws Exception {

        when(taskRepository.updateStatus(4, "DONE"))
                .thenReturn(1);

        mockMvc.perform(put("/api/tasks/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "status": "DONE"
                        }
                        """))
                .andExpect(status().isOk());

        verify(taskRepository).updateStatus(4, "DONE");
    }

    // TEST 5 - DELETE
    @Test
    void shouldDeleteTask() throws Exception {

        when(taskRepository.delete(4))
                .thenReturn(1);

        mockMvc.perform(delete("/api/tasks/4"))
                .andExpect(status().isOk());

        verify(taskRepository).delete(4);
    }
}
