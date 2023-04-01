package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import com.example.todo.dto.UserDto;
import com.example.todo.entities.User;

public interface UserService {

	public UserDto converEntitytoDto(User user);

	public User saveUser(User user);

	public List<User> getAllUsers();

	public Optional<User> getUserById(int id);

	public User updateUser(int id, User user);

	public List<UserDto> getAllUserDto();

	public void addRoles(int userId, int roleId);
	
	public void deleteUser(int id);

}
