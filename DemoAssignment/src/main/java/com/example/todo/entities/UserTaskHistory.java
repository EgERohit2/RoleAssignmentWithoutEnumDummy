package com.example.todo.entities;

import java.util.Date;

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

	public UserTaskHistory(int id, UserTask usertask, TaskStatus status, Date date) {
		super();
		this.id = id;
		this.usertask = usertask;
		this.status = status;
		this.date = date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "usertask_id")
	private UserTask usertask;
	@Enumerated(EnumType.ORDINAL)
	private TaskStatus status;
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UserTaskHistory [id=" + id + ", usertask=" + usertask + ", status=" + status + ", date=" + date + "]";
	}

}
