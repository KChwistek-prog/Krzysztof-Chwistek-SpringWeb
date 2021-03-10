package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.services.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void testCreateTask() throws Exception {
        //Given
        Gson gson = new Gson();
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");
        Task task = new Task(1L, "Test title", "Test content");
        String jsonTask = gson.toJson(taskDto);
        when(dbService.saveTask(any())).thenReturn(task);
        //When & Then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonTask))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    void testUpdateTask() throws Exception {
        //Given
        Gson gson = new Gson();
        TaskDto taskDto = new TaskDto(1L, "Updated test title", "Updated test content");
        Task updatedTask = new Task(1L, "Updated test title", "Updated test content");
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);
        when(taskMapper.mapToTask(any())).thenReturn(updatedTask);
        when(dbService.updateTask(any())).thenReturn(updatedTask);
        String jsonTask = gson.toJson(updatedTask);

        //When & Then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonTask))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Updated test content")));

    }

    @Test
    void testDeleteTask() throws Exception {
        //When & Then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask")
                        .param("taskId", "1")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test title", "Test content");
        when(dbService.getTask(any())).thenReturn(Optional.of(task));
        //When & Then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    void testGetTasks() throws Exception {
        //Given
        TaskDto firstTask = new TaskDto(1L, "Test title", "Test content");
        TaskDto secondTask = new TaskDto(2L, "Test title", "Test content");
        List<TaskDto> taskList = new ArrayList<>();
        List<Task> taskList2 = new ArrayList<>();
        taskList.add(firstTask);
        taskList.add(secondTask);
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskList);
        when(dbService.getAllTasks()).thenReturn(taskList2);
        //When & Then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

}
