package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import com.example.todo.entities.UserTask;
import com.example.todo.entities.UserTaskHistory;

public interface UserTaskService {

	public UserTask saveUserTask(UserTask userTask);

	public List<UserTask> getAllUserTasks();

	public Optional<UserTask> getUserTaskById(int id);

	public UserTask updateUserTask(int id, UserTask userTask);

	public void checkIsTaskOverdue();
	
	public boolean isUserTaskBelongsToUser(int userTaskId, int userId);

}
