package com.crud.tasks;


import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Repository
public interface TaskRepository extends CrudRepository <Task, Long> {
    List<Task> findAll();

    Optional<Task> findById(Long id);
}
