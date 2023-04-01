package com.example.todo.dto;

public class UserDto {

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto(String email, String psk, String mob, String name) {
		super();
		this.email = email;
		this.psk = psk;
		this.mob = mob;
		this.name = name;
	}

	private String email;
	private String psk;
	private String mob;
	private String name;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsk() {
		return psk;
	}

	public void setPsk(String psk) {
		this.psk = psk;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserDto [email=" + email + ", psk=" + psk + ", mob=" + mob + ", name=" + name + "]";
	}

}
