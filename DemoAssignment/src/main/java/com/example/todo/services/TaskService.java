package com.example.todo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.todo.dto.TaskDto;
import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;

public interface TaskService {

	public Task saveTask(Task task);

	public List<Task> getAllTasks();

	public List<TaskDto> getAllTaskDto();

	public TaskDto getTaskDtoById(int id);

	public Task updateTask(int id, Task task);
	
	public Page<TasksDto> getAllwithDto(String pageNumber, String pageSize, String search);
}
