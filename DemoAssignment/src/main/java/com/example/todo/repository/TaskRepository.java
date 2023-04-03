package com.example.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	Page<TasksDto> findByOrderById(Pageable paging, Class<TasksDto> class1);

	Page<TasksDto> findByNameContainingIgnoreCaseOrderById(String trimAllWhitespace, Pageable paging,
			Class<TasksDto> class1);

}
