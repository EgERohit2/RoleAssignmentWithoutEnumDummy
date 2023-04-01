package com.example.todo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.example.todo.dto.UserDto;
import com.example.todo.entities.Role;
import com.example.todo.entities.Task;
import com.example.todo.entities.User;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.impl.TaskServiceImplementation;
import com.example.todo.service.impl.UserServiceImplementation;
import com.example.todo.service.impl.UserTaskServiceImplementation;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserTaskServiceImplementation userTaskServiceImplementation;

	@Autowired
	private UserServiceImplementation userServiceImplementation;

	@Autowired
	private TaskServiceImplementation taskServiceImplementation;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/data")
	public List<User> getAllUsers() {
		return userServiceImplementation.getAllUsers();
	}

	// @PreAuthorize("hasRole('ROLE_admin')")
//	@GetMapping("/{id}")
//	public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
//		Optional<User> user = userServiceImplementation.getUserById(id);
//		if (user.isPresent()) {
//			return ResponseEntity.ok(user.get());
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

	@PostMapping("/data")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		userServiceImplementation.saveUser(user);
		try {
			return new ResponseEntity<>(new SuccessResponseDto("success", "send", null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("Error ", "No data found"), HttpStatus.OK);

		}

	}

//	@PutMapping("/{id}")
//	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
//		User updatedUser = userServiceImplementation.updateUser(id, user);
//
//		return ResponseEntity.ok(updatedUser);
//
//	}

//	@PutMapping("/{id}")
//	public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
//		return userServiceImplementation.updateUser(id, user);
//
//	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		userServiceImplementation.updateUser(id, user);
		try {
			return new ResponseEntity<>(new SuccessResponseDto("success", "send", user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("Error ", "No data found"), HttpStatus.OK);

		}

	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/userDto")
	public List<UserDto> getAllUserDto() {
		return userServiceImplementation.getAllUserDto();
	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@PostMapping("/post")
	public String post(@RequestParam(value = "user_id") int user_id, @RequestParam(value = "role_id") int role_id) {
		this.userServiceImplementation.addRoles(user_id, role_id);
		return "Role assigned";
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") int id) {
		this.userServiceImplementation.deleteUser(id);
		try {
			return new ResponseEntity<>(new SuccessResponseDto("success", "Deleted", null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("Error ", "No data found"), HttpStatus.OK);
		}
	}

	// 5. Status of the task can be updated only by the assigned user and admin.
	// Users cannot update the task status that is not assigned to them
	@PutMapping("/task/update/{id}")
	public ResponseEntity<?> updateTaskById(@PathVariable("id") int id, @RequestBody Task t,
			@RequestHeader("Authorization") String token) {

		// Extract username from token
		String username = JwtUtil.parseToken(token.replace("Bearer ", ""));

		// Fetch user by username
		User user = userRepository.findByUsername(username);
		System.out.println(user);
		if (user.getId() != id && user.getRole() != Role.admin && user.getRole() != Role.EMPLOYEE) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		// Fetch task by ID
		Optional<Task> optionalTask = taskServiceImplementation.getTaskById(id);
		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			TaskDto taskDto = new TaskDto();
			task.setName(t.getName());
			task.setDesc(t.getDesc());
			task.setStartDate(t.getStartDate());
			task.setEndDate(t.getEndDate());

			// Save updated task
			taskServiceImplementation.saveTask(task);
			return ResponseEntity.ok(taskDto);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/tasks")
	public ResponseEntity<?> getTasksByUserId(@RequestHeader("Authorization") String token) {

		// Extract username from token
		String username = JwtUtil.parseToken(token.replace("Bearer ", ""));

		// Fetch user by username
		User user = userRepository.findByUsername(username);

		// Fetch tasks assigned to user
		Optional<Task> tasks = taskServiceImplementation.getTaskById(user.getId());

		// Convert tasks to DTOs
		List<TaskDto> taskDtos = tasks.stream()
				.map(task -> new TaskDto(task.getName(), task.getDesc(), task.getEndDate(), task.getStartDate()))
				.collect(Collectors.toList());
		System.out.println(taskDtos);
		return ResponseEntity.ok(taskDtos);
	}

}
