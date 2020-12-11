package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.services.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.InvalidAttributeValueException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask/{id}")
    public Task getTask(@PathVariable("id") Long id) throws InvalidAttributeValueException {
      Optional<Task> task = service.getTask(id);
       return task.orElseThrow(InvalidAttributeValueException::new);
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
