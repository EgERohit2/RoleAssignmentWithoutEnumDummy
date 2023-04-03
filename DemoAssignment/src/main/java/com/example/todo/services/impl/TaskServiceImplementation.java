package com.example.todo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.todo.dto.TaskDto;
import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.services.TaskService;

@Service
public class TaskServiceImplementation implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task saveTask(Task task) {
		// TODO Auto-generated method stub
		return taskRepository.save(task);
	}

	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return taskRepository.findAll();
	}

	@Override
	public Task updateTask(int id, Task task) {
		// TODO Auto-generated method stub
		Optional<Task> optionalTask = taskRepository.findById(id);
		if (optionalTask.isPresent()) {
			Task existingTask = optionalTask.get();
			existingTask.setName(task.getName());
			existingTask.setDesc(task.getDesc());
			existingTask.setUsertask(task.getUsertask());
			return taskRepository.save(existingTask);
		} else {
			return null;
		}
	}

	@Override
	public List<TaskDto> getAllTaskDto() {
		List<Task> t = taskRepository.findAll();
		List<TaskDto> taskDto = new ArrayList<>();
		for (int i = 0; i < t.size(); i++) {
			TaskDto td = new TaskDto();
			td.setName(t.get(i).getName());
			td.setDesc(t.get(i).getDesc());
			taskDto.add(td);
		}
		return taskDto;
	}

	@Override
	public TaskDto getTaskDtoById(int id) {
		// TODO Auto-generated method stub
		Optional<Task> optionalTask = taskRepository.findById(id);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			TaskDto taskDto = new TaskDto();
			taskDto.setName(task.getName());
			taskDto.setDesc(task.getDesc());
			return taskDto;
		} else {
			return null; // or throw an exception
		}
	}

	@Override
	public Page<TasksDto> getAllwithDto(String pageNumber, String pageSize, String search) {
		Pageable paging = PageRequest.of(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize));
		Page<TasksDto> cvList;
		if ((search == "") || (search == null) || (search.length() == 0)) {
			cvList = taskRepository.findByOrderById(paging, TasksDto.class);
		} else {
			cvList = taskRepository.findByNameContainingIgnoreCaseOrderById(StringUtils.trimAllWhitespace(search),
					paging, TasksDto.class); 
		}

		return cvList;
	}

}
