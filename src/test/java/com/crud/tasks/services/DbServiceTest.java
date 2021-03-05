package com.crud.tasks.services;

import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Test
    void testSaveTask() {
        //Given
        Task task = new Task("test", "test");
        //When
        dbService.saveTask(task);
        //Then
        Assertions.assertEquals(task.getTitle(), dbService.getTask(1L).get().getTitle());
        //CleanUp
        dbService.deleteTask(task.getId());
    }

    @Test
    void testGetAllTasks() {
        //Given
        Task firstTask = new Task( "test", "test");
        Task secondTask = new Task("test", "test");
        dbService.saveTask(firstTask);
        dbService.saveTask(secondTask);
        //When
        List<Task> listOfTasks = dbService.getAllTasks();
        //Then
        Assertions.assertNotNull(listOfTasks);
        //CleanUp
        dbService.deleteTask(firstTask.getId());
        dbService.deleteTask(secondTask.getId());
    }

    @Test
    void testGetTask() {
        //Given
        Task task = new Task( "test", "test");
        dbService.saveTask(task);
        ///When & Then
        Assertions.assertEquals(task.getTitle(), dbService.getTask(task.getId()).get().getTitle());
        //CleanUp
        dbService.deleteTask(task.getId());
    }

    @Test
    void testUpdateTask() {
        //Given
        Task task = new Task( "test", "test");
        dbService.saveTask(task);
        //When
        Task updatedTask = new Task( "updated task", "updated task");
        dbService.updateTask(updatedTask);
        //Then
        Assertions.assertEquals(updatedTask.getTitle(), dbService.getTask(updatedTask.getId()).get().getTitle());
        //CleanUp
        dbService.deleteTask(task.getId());
    }

    @Test
    void testDeleteTask() {
        //Given
        Task task = new Task( "test", "test");
        dbService.saveTask(task);
        //When
        dbService.deleteTask(task.getId());
        //Then
        Assertions.assertTrue(dbService.getTask(task.getId()).isEmpty());
    }
}