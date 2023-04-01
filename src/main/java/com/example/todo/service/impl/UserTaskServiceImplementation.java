package com.example.todo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entities.Task;
import com.example.todo.entities.User;
import com.example.todo.entities.UserTask;
import com.example.todo.entities.UserTaskHistory;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserTaskHistoryRepository;
import com.example.todo.repository.UserTaskRepository;
import com.example.todo.service.UserTaskService;

@Service
public class UserTaskServiceImplementation implements UserTaskService {

	@Autowired
	private UserTaskRepository userTaskRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserTaskHistoryRepository userTaskHistoryRepository;

	@Override
	public UserTask saveUserTask(UserTask userTask) {
		// TODO Auto-generated method stub
		return userTaskRepository.save(userTask);
	}

	@Override
	public List<UserTask> getAllUserTasks() {
		// TODO Auto-generated method stub
		return userTaskRepository.findAll();
	}

	@Override
	public Optional<UserTask> getUserTaskById(int id) {
		// TODO Auto-generated method stub
		return userTaskRepository.findById(id);
	}

	@Override
	public UserTask updateUserTask(int id, UserTask userTask) {
		// TODO Auto-generated method stub
		Optional<UserTask> optionalUserTask = userTaskRepository.findById(id);
		if (optionalUserTask.isPresent()) {
			UserTask existingUserTask = optionalUserTask.get();
			existingUserTask.setUser(userTask.getUser());
			existingUserTask.setTask(userTask.getTask());
			existingUserTask.setStatus(userTask.getStatus());
			existingUserTask.setUserTaskHistory(userTask.getUserTaskHistory());

			UserTask u = userTaskRepository.save(existingUserTask);

			UserTask u1 = userTaskRepository.findByUserIdAndTaskId(userTask.getUser().getId(),
					existingUserTask.getTask().getId());

			UserTask uId = new UserTask();
			uId.setId(u1.getId());

			UserTask st = new UserTask();
			st.setStatus(u1.getStatus());

			UserTaskHistory u11 = new UserTaskHistory();
			u11.setUsertask(uId);
			u11.setStatus(st.getStatus());

			userTaskHistoryRepository.save(u11);

			return u;
		} else {
			return null;
		}
	}

	@Override
	public void checkIsTaskOverdue() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUserTaskBelongsToUser(int userTaskId, int userId) {
		
	        Optional<UserTask> optionalUserTask = userTaskRepository.findById(userTaskId);
	        if (optionalUserTask.isPresent()) {
	            UserTask userTask = optionalUserTask.get();
	            User user = userTask.getUser();
	            return user.getId() == userId;
	        } else {
	            throw new EntityNotFoundException("User Task not found with id " + userTaskId);
	        }

}
}
