package com.example.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.entities.Task;
import com.example.todo.entities.User;
import com.example.todo.entities.UserTask;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {

	UserTask findByUserIdAndTaskId(Integer userId, Integer taskId);

	List<UserTask> findByTask(Task task);

	List<UserTask> findByUser(User user);

	List<UserTask> findByUser(int userId);
	
	
}
