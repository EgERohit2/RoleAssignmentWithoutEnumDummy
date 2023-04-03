package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.UserDto;
import com.example.todo.entities.User;
import com.example.todo.services.UserService;

@RestController
@RequestMapping("/to-do")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/data")
	public String postAllData(@RequestBody User user) {
		userService.saveUser(user);
		return "posted";
	}

	@GetMapping("/user")
	public List<User> getAllData() {
		return userService.getAllUsers();
	}

	@GetMapping("user/data/{id}")
	public void getDataById(@RequestParam(value = "id") int id, @RequestBody User user) {
		userService.getUserById(id);
	}

	@DeleteMapping("user/data/delete/{id}")
	public String deleteDataById(@RequestParam(value = "id") int id) {
		userService.deleteUser(id);
		return "deleted";
	}

	@PostMapping("/post/assignRoles")
	public String post(@RequestParam(value = "user_id") int user_id, @RequestParam(value = "role_id") int role_id) {
		this.userService.addRoles(user_id, role_id);
		return "Role assigned";
	}

	@GetMapping("/userDto/getAllDto")
	public List<UserDto> getAllDto() {
		return userService.getAllUserDto();
	}

}
