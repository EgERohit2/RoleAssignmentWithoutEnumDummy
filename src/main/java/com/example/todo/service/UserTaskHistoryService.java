package com.example.todo.service;

import com.example.todo.entities.UserTaskHistory;

public interface UserTaskHistoryService {

	public UserTaskHistory saveUserTaskHistory(UserTaskHistory userTaskHistory);

	public UserTaskHistory updateUserTaskHistory(int id, UserTaskHistory userTaskHistory);

}
