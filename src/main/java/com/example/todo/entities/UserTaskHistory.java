package com.example.todo.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserTaskHistory {

	public UserTaskHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserTaskHistory(int id, UserTask usertask, TaskStatus status) {
		super();
		this.id = id;
		this.usertask = usertask;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "usertask_id")
	private UserTask usertask;
	@Enumerated(EnumType.ORDINAL)
	private TaskStatus status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserTask getUsertask() {
		return usertask;
	}

	public void setUsertask(UserTask usertask) {
		this.usertask = usertask;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserTaskHistory [id=" + id + ", usertask=" + usertask + ", status=" + status + "]";
	}

}
