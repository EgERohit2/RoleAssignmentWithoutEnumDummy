package com.example.todo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UserTask {

	public UserTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserTask(int id, User user, Task task, TaskStatus status, List<UserTaskHistory> userTaskHistory) {
		super();
		this.id = id;
		this.user = user;
		this.task = task;
		this.status = status;
		this.userTaskHistory = userTaskHistory;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	private Task task;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	@OneToMany(mappedBy = "usertask", cascade = CascadeType.ALL)
	private List<UserTaskHistory> userTaskHistory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public List<UserTaskHistory> getUserTaskHistory() {
		return userTaskHistory;
	}

	public void setUserTaskHistory(List<UserTaskHistory> userTaskHistory) {
		this.userTaskHistory = userTaskHistory;
	}

	@Override
	public String toString() {
		return "UserTask [id=" + id + ", user=" + user + ", task=" + task + ", status=" + status + ", userTaskHistory="
				+ userTaskHistory + "]";
	}

}
