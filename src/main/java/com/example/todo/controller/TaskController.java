package com.example.todo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.component.JwtUtil;
import com.example.todo.dto.ErrorResponseDto;
import com.example.todo.dto.SuccessResponseDto;
import com.example.todo.dto.TaskDto;
import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Role;
import com.example.todo.entities.Task;
import com.example.todo.entities.TaskStatus;
import com.example.todo.entities.User;
import com.example.todo.entities.UserTask;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.repository.UserTaskRepository;
import com.example.todo.service.impl.TaskServiceImplementation;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskServiceImplementation taskServiceImplementation;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTaskRepository userTaskRepository;

	@Autowired
	private TaskRepository taskRepository;

	@GetMapping("")
	public List<Task> getAllTasks() {
		return taskServiceImplementation.getAllTasks();
	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/taskDto")
	public List<TaskDto> getAllTasksDto() {
		return taskServiceImplementation.getAllTaskDto();
	}

//	@PreAuthorize("hasRole('ROLE_admin')")
//	@GetMapping("/{id}")
//	public ResponseEntity<TaskDto> getTaskById1(@PathVariable("id") int id) {
//		Optional<TaskDto> task = taskServiceImplementation.getTaskDtoById(id);
//		if (task.isPresent()) {
//			return ResponseEntity.ok(task.get());
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/Dto/taskDto/{id}")
	public Optional<TaskDto> getTaskDtoById(@PathVariable int id) {
		Optional<TaskDto> taskDto = taskServiceImplementation.getTaskDtoById(id);
		return taskDto;
	}

	// @PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/{id}")
	public Task getTaskById(@PathVariable int id) {
		return taskServiceImplementation.getTaskById(id).orElseThrow();
	}

	@PostMapping("/data")
	public ResponseEntity<Task> saveTask(@RequestBody Task task) {
		return ResponseEntity.ok(taskServiceImplementation.saveTask(task));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") int id, @RequestBody Task task) {
		Task updatedTask = taskServiceImplementation.updateTask(id, task);
		if (updatedTask != null) {
			return ResponseEntity.ok(updatedTask);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("/{id}/update-status")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") int id) {
		Optional<Task> optionalTask = taskServiceImplementation.getTaskById(id);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			Date currentDate = new Date();
			if (currentDate.after(task.getEndDate())) {
				task.setStatus(TaskStatus.OVERDUE);
			} else {
				task.setStatus(TaskStatus.INPROGRESS);
			}
			taskServiceImplementation.saveTask(task);
			return ResponseEntity.ok("Task status updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//
//	@GetMapping("/tasks/user/{id}")
//	public List<TaskDto> getTasksByUserId(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
//		String username = JwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
//		User user = userRepository.findByUsername(username);
//		if (user == null || user.getId() != id) {
//			throw new Exception("Unauthorized access");
//		}
//		Optional<Task> tasks = taskServiceImplementation.getTaskById(id);
//		return tasks.stream().map(task -> TaskDto.fromTask(task)).collect(Collectors.toList());
//	}
	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/pagination")
	public ResponseEntity<?> getAllPagination(@RequestParam(value = "search") String search,
			@RequestParam(value = "pageNumber") String pageNumber, @RequestParam(value = "pageSize") String pageSize) {

		Page<TasksDto> cvs = taskServiceImplementation.getAllTaskDto(search, pageNumber, pageSize);
		System.out.println("@@" + cvs);
		if (cvs.getTotalElements() != 0) {
			return new ResponseEntity<>((cvs.getContent()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		}

//	@PreAuthorize("hasRole('ROLE_admin')")
//	@GetMapping("/pagination")
//	public ResponseEntity<?> getAllPaginations(@RequestParam(value="search") String search,@RequestParam(value = "pageNumber") String pageNumber,
//			@RequestParam(value = "pageSize") String pageSize) {
//		
//		Page<TaskDto> cvs = taskServiceImplementation.getAllTaskDtoPagination(search,pageNumber, pageSize);
//		if (cvs.getTotalElements() != 0) {
//			return new ResponseEntity<>((cvs.getContent()), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
//		}
//	}
	}

	@PreAuthorize("hasRole('ROLE_admin')||hasRole('ROLE_EMPLOYEE')")
	@GetMapping("/page")
	public ResponseEntity<?> pagination(@RequestParam(value = "pageNumber") int pageNumber,
			@RequestParam(value = "pageSize") int pageSize) {
		List<Task> database = taskRepository.findAll();
		if (!database.isEmpty()) {
			Page<TaskDto> list1 = this.taskServiceImplementation.getAllwithDto(pageNumber, pageSize);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "done", list1), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("Error ", "No data found"), HttpStatus.OK);
		}
	}

	@GetMapping("/tasks")
	public ResponseEntity<?> getAllTasksForUser(@RequestHeader("Authorization") String token) {

		// Extract username from token
		String username = JwtUtil.parseToken(token.replace("Bearer ", ""));

		// Fetch user by username
		User user = userRepository.findByUsername(username);

		// Fetch all tasks assigned to user
		List<TaskDto> taskDtos = new ArrayList<>();
		List<UserTask> userTasks = userTaskRepository.findByUser(user);

		for (UserTask userTask : userTasks) {
			Task task = userTask.getTask();
			TaskDto taskDto = new TaskDto(task.getName(), task.getDesc(), task.getEndDate(), task.getStartDate());
			taskDtos.add(taskDto);
		}

		return ResponseEntity.ok(taskDtos);
	}
	

	@GetMapping("/tasks/{userId}")
	public ResponseEntity<?> getAllTasksForUser(@RequestParam int userId,
			@RequestHeader("Authorization") String token) {

		// Extract username from token
		String username = JwtUtil.parseToken(token.replace("Bearer ", ""));

		// Fetch user by username
		User user = userRepository.findByUsername(username);

		// Check if the user is an admin or the user whose tasks are being fetched
		if (user.getRole() != Role.admin && user.getId() != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		// Fetch all tasks assigned to user
		List<TaskDto> taskDtos = new ArrayList<>();
		User userById = userRepository.findById(userId).orElse(null);
		if (userById != null) {
			List<UserTask> userTasks = userTaskRepository.findByUser(userById);

			for (UserTask userTask : userTasks) {
				Task task = userTask.getTask();
				TaskDto taskDto = new TaskDto(task.getName(), task.getDesc(), task.getEndDate(), task.getStartDate());
				taskDtos.add(taskDto);
			}
		}

		return ResponseEntity.ok(taskDtos);
	}

	// 31 mar 20203
//	(not working)
	@GetMapping("search")
	public ResponseEntity<?> getAll(@RequestParam(value = "search", required = false) String search) {

		List<Task> database = this.taskRepository.findAll();

		if (!database.isEmpty()) {
			try {
				List<Task> list = this.taskRepository.findBySearch(search);
				return new ResponseEntity<>(new SuccessResponseDto("success", "Success", list), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new ErrorResponseDto("Something Went Wrong", e.getMessage()),
						HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<>(new ErrorResponseDto("dataNotFound", "Data Not Found"), HttpStatus.NOT_FOUND);
		}

	}
	
//	(not working)
	// 31 mar 20203
	
	//(not working)
//	@PutMapping("/tasks/{taskId}")
//	public ResponseEntity<?> updateTaskForUser(@PathVariable int taskId,
//	                                            @RequestBody Task task,
//	                                            @RequestHeader("Authorization") String token) {
//
//	    // Extract username from token
//	    String username = JwtUtil.parseToken(token.replace("Bearer ", ""));
//
//	    // Fetch user by username
//	    User user = userRepository.findByUsername(username);
//
//	    // Fetch user task by task ID and user ID
//	    UserTask userTask = userTaskRepository.findByUserIdAndTaskId(taskId, user.getId());
//
//	    if (userTask == null) {
//	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//	                             .body(new ErrorResponseDto("Task not found or not assigned to user", username));
//	    }
//
//	    // Update task
//	    Task tasks = userTask.getTask();
//	    task.setName(task.getName());
//	    task.setDesc(task.getDesc());
//	    task.setStartDate(task.getStartDate());
//	    task.setEndDate(task.getEndDate());
//	    taskRepository.save(tasks);
//
//	    return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDto("done", "done", tasks));
//	}
//
//	
}
