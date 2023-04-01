package com.example.todo.dto;

import java.util.Date;

public class TaskDto {

	public TaskDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskDto(String name, String desc, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	private String name;
	private String desc;
	private Date startDate;
	private Date endDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	@Override
	public String toString() {
		return "TaskDto [name=" + name + ", desc=" + desc + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
