package com.example.todo.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entities.Task;
import com.example.todo.entities.TaskStatus;
import com.example.todo.entities.UserTask;
import com.example.todo.entities.UserTaskHistory;
import com.example.todo.services.UserTaskHistoryService;
import com.example.todo.services.UserTaskService;

@RestController
@RequestMapping("/to-do")
public class UserTaskController {

	@Autowired
	private UserTaskService userTaskService;

	@Autowired
	private UserTaskHistoryService userTaskHistoryService;

	@PostMapping("/userTask/data")
	public String postAllData(@RequestBody UserTask userTask) {
		userTaskService.saveUserTask(userTask);
		return "posted";
	}

	@GetMapping("/{id}")
	public UserTask getUserTaskById(@PathVariable("id") int id) {
		UserTask userTask = userTaskService.getUserTaskById(id).orElseThrow();
		return userTask;

	}

	@PutMapping("/{id}")
	public ResponseEntity<UserTask> updateUserTask(@PathVariable("id") int id, @RequestBody UserTask userTask) {
		UserTask updatedUserTask = userTaskService.updateUserTask(id, userTask);
		if (updatedUserTask != null) {
			return ResponseEntity.ok(updatedUserTask);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	@PutMapping("/{id}/update-status")
//	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") int id) {
//		Optional<UserTask> optionalUserTask = userTaskService.getUserTaskById(id);
//		if (optionalUserTask.isPresent()) {
//			System.out.println(optionalUserTask);
//			UserTask userTask = optionalUserTask.get();
//			Task task = userTask.getTask();
//			Date currentDate = new Date();
//			if (currentDate.after(userTask.getEndDate())) {
//				userTask.setStatus(TaskStatus.OVERDUE);
//			} else {
//				userTask.setStatus(TaskStatus.INPROGRESS);
//			}
//			userTaskService.saveUserTask(userTask);
//
//			// Add new entry in UserTaskHistory
//			UserTaskHistory userTaskHistory = new UserTaskHistory();
//			userTaskHistory.setUsertask(userTask);
//			userTaskHistory.setStatus(userTask.getStatus());
//			userTaskHistoryService.saveUserTaskHistory(userTaskHistory);
//
//			return ResponseEntity.ok("Task status updated successfully");
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

	// 03-04-2023
	@PutMapping("/{id}/update-status")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") int id) {
		Optional<UserTask> optionalUserTask = userTaskService.getUserTaskById(id);
		if (optionalUserTask.isPresent()) {
			UserTask userTask = optionalUserTask.get();
			if (userTask.getStatus() == TaskStatus.DONE) {
				return ResponseEntity.badRequest().body("Task has already been completed");
			}
			Date currentDate = new Date();
			if (currentDate.after(userTask.getEndDate())) {
				userTask.setStatus(TaskStatus.OVERDUE);
			} else {
				userTask.setStatus(TaskStatus.INPROGRESS);
			}
			userTaskService.saveUserTask(userTask);

			UserTaskHistory userTaskHistory = new UserTaskHistory();
			userTaskHistory.setUsertask(userTask);
			userTaskHistory.setStatus(userTask.getStatus());
			userTaskHistory.setDate(new Date());
			userTaskHistoryService.saveUserTaskHistory(userTaskHistory);

			return ResponseEntity.ok("Task status updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
