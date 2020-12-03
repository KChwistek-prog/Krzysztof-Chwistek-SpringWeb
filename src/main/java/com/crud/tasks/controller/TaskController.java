package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @GetMapping("getTasks")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping("getTask")
    public TaskDto getTask(Long taskId) {
        return new TaskDto(1L, "test title", "test_content");
    }

    @DeleteMapping("deleteTask")
    public void deleteTask(Long taskId) {

    }

    @PostMapping("updateTask")
    public TaskDto updateTask(TaskDto task) {
        return new TaskDto(1L, "updated title", "updated content");
    }

    @PutMapping("createTask")
    public void createTask(TaskDto task) {

    }
}
