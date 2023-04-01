package com.example.todo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.ErrorResponseDto;
import com.example.todo.dto.SuccessResponseDto;
import com.example.todo.entities.Task;
import com.example.todo.entities.TaskStatus;
import com.example.todo.entities.UserTask;
import com.example.todo.entities.UserTaskHistory;
import com.example.todo.service.impl.UserTaskHistoryServiceImplementation;
import com.example.todo.service.impl.UserTaskServiceImplementation;

@RestController
@RequestMapping("/usertask")
public class UserTaskController {

	@Autowired
	private UserTaskServiceImplementation userTaskServiceImplementation;

	@Autowired
	private UserTaskHistoryServiceImplementation userTaskHistoryServiceImplementation;

	@GetMapping("data")
	public List<UserTask> getAllUserTasks() {
		return userTaskServiceImplementation.getAllUserTasks();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserTaskById(@PathVariable("id") int id) {
		Optional<UserTask> userTask = userTaskServiceImplementation.getUserTaskById(id);
		
		try {
			return new ResponseEntity<>(new SuccessResponseDto("success", "fetched", userTask),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("Error ", "No data found"), HttpStatus.OK);
		}
	}

	@PostMapping("/data")
	public ResponseEntity<UserTask> saveUserTask(@RequestBody UserTask userTask) {
		return ResponseEntity.ok(userTaskServiceImplementation.saveUserTask(userTask));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserTask> updateUserTask(@PathVariable("id") int id, @RequestBody UserTask userTask) {
		UserTask updatedUserTask = userTaskServiceImplementation.updateUserTask(id, userTask);
		if (updatedUserTask != null) {
			return ResponseEntity.ok(updatedUserTask);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}/update-status")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") int id) {
		Optional<UserTask> optionalUserTask = userTaskServiceImplementation.getUserTaskById(id);
		if (optionalUserTask.isPresent()) {
			UserTask userTask = optionalUserTask.get();
			Task task = userTask.getTask();
			Date currentDate = new Date();
			if (currentDate.after(task.getEndDate())) {
				userTask.setStatus(TaskStatus.OVERDUE);
			} else {
				userTask.setStatus(TaskStatus.INPROGRESS);
			}
			userTaskServiceImplementation.saveUserTask(userTask);

			// Add new entry in UserTaskHistory
			UserTaskHistory userTaskHistory = new UserTaskHistory();
			userTaskHistory.setUsertask(userTask);
			userTaskHistory.setStatus(userTask.getStatus());
			userTaskHistoryServiceImplementation.saveUserTaskHistory(userTaskHistory);

			return ResponseEntity.ok("Task status updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
