package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.TaskDto;
import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;
import com.example.todo.services.TaskService;

@RestController
@RequestMapping("/to-do")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("task/data")
	public String postAllData(@RequestBody Task task) {
		taskService.saveTask(task);
		return "posted";
	}

	@GetMapping("task")
	public List<Task> getAllData() {
		return taskService.getAllTasks();
	}

	@GetMapping("taskDto/getAllDto")
	public List<TaskDto> getAllTaskDto() {
		return taskService.getAllTaskDto();
	}

	@PutMapping("update/task")
	public String updateTask(@RequestParam(value = "task_id") int id, @RequestBody Task task) {
		taskService.updateTask(id, task);
		return "updated";
	}

	@GetMapping("taskDto/data")
	public TaskDto updateTaskById(@RequestParam(value = "id") int id) {
		TaskDto t = taskService.getTaskDtoById(id);
		return t;
	}
	//not working
	@GetMapping("/pagination")
	public ResponseEntity<?> getAllPagination(@RequestParam(value = "search") String search,
			@RequestParam(value = "pageNumber") String pageNumber, @RequestParam(value = "pageSize") String pageSize) {

		Page<TasksDto> cvs = taskService.getAllwithDto(search, pageNumber, pageSize);
		System.out.println("@@" + cvs);
		if (cvs.getTotalElements() != 0) {
			return new ResponseEntity<>((cvs.getContent()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		}
	}
}
