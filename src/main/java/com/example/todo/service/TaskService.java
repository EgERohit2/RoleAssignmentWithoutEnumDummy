package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.todo.dto.TaskDto;
import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;

public interface TaskService {

	public Task saveTask(Task task);

	public List<Task> getAllTasks();

	public Optional<Task> getTaskById(int id);

	public Task updateTask(int id, Task task);
	
	public TaskDto converEntitytoDto(Task task);
	
	public List<TaskDto> getAllTaskDto();
	
	public Optional<TaskDto> getTaskDtoById(int id);

	Page<TasksDto> getAllTaskDto(String search, String pageNumber, String pageSize);
	
	public Page<TaskDto> getAllwithDto(int pageNumber, int pageSize);
	
	
	
	//Page<TaskDto> getAllTaskDtoPagination(String search, String pageNumber, String pageSize);

//	 public void checkOverdueTasks();

//	public void checkOverdueTasksAndUpdateStatus();

}
