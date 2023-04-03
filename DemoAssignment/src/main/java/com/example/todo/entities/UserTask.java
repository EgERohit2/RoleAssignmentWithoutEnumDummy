package com.example.todo.entities;

import java.util.Date;
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

	public UserTask(int id, User user, Task task, TaskStatus status, Date startDate, Date endDate,
			List<UserTaskHistory> userTaskHistory) {
		super();
		this.id = id;
		this.user = user;
		this.task = task;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
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
	private TaskStatus status = TaskStatus.TODO;
	private Date startDate;
	private Date endDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<UserTaskHistory> getUserTaskHistory() {
		return userTaskHistory;
	}

	public void setUserTaskHistory(List<UserTaskHistory> userTaskHistory) {
		this.userTaskHistory = userTaskHistory;
	}

	@Override
	public String toString() {
		return "UserTask [id=" + id + ", user=" + user + ", task=" + task + ", status=" + status + ", startDate="
				+ startDate + ", endDate=" + endDate + ", userTaskHistory=" + userTaskHistory + "]";
	}

}
