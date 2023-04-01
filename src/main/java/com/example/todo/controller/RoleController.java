package com.example.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.entities.Role;
import com.example.todo.service.impl.RoleServiceImplementation;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleServiceImplementation roleServiceImplementation;

	@PostMapping("/data")
	public String postData(@RequestBody Role role) {
		roleServiceImplementation.postAllData(role);
		return "posted";
	}
}
