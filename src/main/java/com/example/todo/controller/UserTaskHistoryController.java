package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entities.UserTaskHistory;
import com.example.todo.service.impl.UserTaskHistoryServiceImplementation;

@RestController
@RequestMapping("/usertaskhistory")
public class UserTaskHistoryController {

	@Autowired
	private UserTaskHistoryServiceImplementation userTaskHistoryServiceImplementation;

	@PostMapping("data")
	public ResponseEntity<UserTaskHistory> saveUserTaskHistory(@RequestBody UserTaskHistory userTaskHistory) {
		return ResponseEntity.ok(userTaskHistoryServiceImplementation.saveUserTaskHistory(userTaskHistory));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserTaskHistory> updateUserTaskHistory(@PathVariable("id") int id,
			@RequestBody UserTaskHistory userTaskHistory) {
		UserTaskHistory updatedUserTaskHistory = userTaskHistoryServiceImplementation.updateUserTaskHistory(id,
				userTaskHistory);
		if (updatedUserTaskHistory != null) {
			return ResponseEntity.ok(updatedUserTaskHistory);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
