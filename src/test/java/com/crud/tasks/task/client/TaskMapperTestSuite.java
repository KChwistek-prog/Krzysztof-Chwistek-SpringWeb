package com.crud.tasks.task.client;


import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskMapperTestSuite {

    public static TaskMapper taskMapper = new TaskMapper();

    @Test
    void TestMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "test content");

        //When
        Task mappedToTask = taskMapper.mapToTask(taskDto);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, mappedToTask.getId()),
                () -> Assertions.assertEquals("test", mappedToTask.getTitle()),
                () -> Assertions.assertEquals("test content", mappedToTask.getContent()));
    }

    @Test
    void TestMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test", "test content");

        //When
        TaskDto mappedToTaskDto = taskMapper.mapToTaskDto(task);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, mappedToTaskDto.getId()),
                () -> Assertions.assertEquals("test", mappedToTaskDto.getTitle()),
                () -> Assertions.assertEquals("test content", mappedToTaskDto.getContent()));
    }

    @Test
    void TestMapToTaskDtoList() {
        //Given
        Task task = new Task(1L, "test", "test content");
        List<Task> newTaskList = new ArrayList<>();
        newTaskList.add(task);

        //When
        List<TaskDto> mappedTaskList = taskMapper.mapToTaskDtoList(newTaskList);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(mappedTaskList),
                () -> Assertions.assertEquals(1L, mappedTaskList.get(0).getId()),
                () -> Assertions.assertEquals("test", mappedTaskList.get(0).getTitle()),
                () -> Assertions.assertEquals("test content", mappedTaskList.get(0).getContent()));
    }
}
