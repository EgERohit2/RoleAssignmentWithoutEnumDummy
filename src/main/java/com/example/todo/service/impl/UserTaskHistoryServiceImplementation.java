package com.example.todo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entities.UserTaskHistory;
import com.example.todo.repository.UserTaskHistoryRepository;
import com.example.todo.service.UserTaskHistoryService;

@Service
public class UserTaskHistoryServiceImplementation implements UserTaskHistoryService {

	@Autowired
	private UserTaskHistoryRepository userTaskHistoryRepository;

	@Override
	public UserTaskHistory saveUserTaskHistory(UserTaskHistory userTaskHistory) {
		// TODO Auto-generated method stub
		return userTaskHistoryRepository.save(userTaskHistory);
	}

	@Override
	public UserTaskHistory updateUserTaskHistory(int id, UserTaskHistory userTaskHistory) {
		// TODO Auto-generated method stub
		Optional<UserTaskHistory> optionalUserTaskHistory = userTaskHistoryRepository.findById(id);
		if (optionalUserTaskHistory.isPresent()) {
			UserTaskHistory existingUserTaskHistory = optionalUserTaskHistory.get();
			existingUserTaskHistory.setUsertask(userTaskHistory.getUsertask());
			return userTaskHistoryRepository.save(existingUserTaskHistory);
		} else {
			return null;
		}
	}

}
