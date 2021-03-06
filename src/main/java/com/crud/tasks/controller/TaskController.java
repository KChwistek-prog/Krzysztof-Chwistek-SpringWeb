package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.services.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    private final DbService service;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(DbService service, TaskMapper taskMapper) {
        this.service = service;
        this.taskMapper = taskMapper;
    }

    @GetMapping(value = "/getTasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "/getTask/{id}")
    public Task getTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        Optional<Task> task = service.getTask(id);
        return task.orElseThrow(TaskNotFoundException::new);
    }

    @DeleteMapping(value = "/deleteTask")
    public void deleteTask(@RequestParam("taskId") Long taskId) throws TaskNotFoundException {
        service.deleteTask(taskId);
    }

    @PutMapping(value = "/updateTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        Task savedTask = service.saveTask(taskMapper.mapToTask(taskDto));
        return taskMapper.mapToTaskDto(savedTask);
    }

    @PostMapping(value = "/createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Task createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        return service.saveTask(task);
    }
}
