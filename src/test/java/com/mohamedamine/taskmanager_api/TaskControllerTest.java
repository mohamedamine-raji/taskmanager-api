
package com.mohamedamine.taskmanager_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verifyNoInteractions;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(TaskController.class)

class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

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

        verifyNoInteractions(jdbcTemplate);
    }

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

        verify(jdbcTemplate).update(
                anyString(),
                eq("Learn Testing"),
                eq("Practice API tests"));
    }

    @Test
    void shouldGetAllTasks() throws Exception {

        when(jdbcTemplate.queryForList(anyString()))
                .thenReturn(List.of(
                        Map.of(
                                "id", 1,
                                "title", "Learn Java",
                                "description", "Practice OOP",
                                "status", "DONE")));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Learn Java"))
                .andExpect(jsonPath("$[0].status").value("DONE"));
    }

    @Test
    void shouldUpdateTaskStatus() throws Exception {

        when(jdbcTemplate.update(
                anyString(),
                eq("DONE"),
                eq(4))).thenReturn(1);

        mockMvc.perform(put("/api/tasks/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "status": "DONE"
                        }
                        """))
                .andExpect(status().isOk());

        verify(jdbcTemplate).update(
                anyString(),
                eq("DONE"),
                eq(4));
    }

    @Test
    void shouldDeleteTask() throws Exception {

        // Simuler la suppression d'une tâche
        when(jdbcTemplate.update(
                anyString(),
                eq(4))).thenReturn(1);

        // Envoyer une requête DELETE
        mockMvc.perform(delete("/api/tasks/4"))
                .andExpect(status().isOk());

        // Vérifier que DELETE a été exécuté
        verify(jdbcTemplate).update(
                anyString(),
                eq(4));
    }

}
