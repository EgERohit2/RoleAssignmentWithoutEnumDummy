package com.example.todo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.dto.UserDto;
import com.example.todo.entities.Role;
import com.example.todo.entities.User;
import com.example.todo.repository.RoleRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		User user1 = new User();
		user1.setEmail(user.getEmail());
		user1.setMob(user.getMob());
		user1.setUsername(user.getUsername());
		String pass = user.getPassword();
		String s = passwordEncoder.encode(pass);
		user1.setPassword(s);
		return userRepository.save(user1);

	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(int id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public User updateUser(int id, User user) {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		// TODO Auto-generated method stub
//		Optional<User> optionalUser = userRepository.findById(id);
		User optionalUser = userRepository.findByIdAndIsActiveTrue(id).orElseThrow();

//			User existingUser = optionalUser.get();
		optionalUser.setUsername(user.getUsername());
		optionalUser.setEmail(user.getEmail());
		optionalUser.setPassword(user.getPassword());
		optionalUser.setMob(user.getMob());
//			existingUser.setUsertask(user.getUsertask());
		return userRepository.save(optionalUser);
	}

//	@Override
//	public List<UserDto> converEntitytoDto() {
//		List<User> u = userRepository.findAll();
//		List<UserDto> userDto = new ArrayList<>();
//		for (int i = 0; i < userDto.size(); i++) {
//			UserDto ud = new UserDto();
//			ud.setEmail(u.get(i).getEmail());
//			ud.setMob(u.get(i).getMob());
//			ud.setName(u.get(i).getName());
//			ud.setPsk(u.get(i).getPsk());
//			userDto.add(ud);
//
//		}
//		return userDto;
//	}

	@Override
	public UserDto converEntitytoDto(User user) {
		// TODO Auto-generated method stub
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		UserDto userDto = new UserDto();
		userDto = modelMapper.map(user, UserDto.class);

		return userDto;
	}

	@Override
	public List<UserDto> getAllUserDto() {
		// TODO Auto-generated method stub
		return userRepository.findAll().stream().map(this::converEntitytoDto).collect(Collectors.toList());
	}

	@Override
	public void addRoles(int userId, int roleId) {
		// TODO Auto-generated method stub
		User u = userRepository.findById(userId).orElseThrow();
		Role r = roleRepository.findById(roleId).orElseThrow();

		List<Role> userRole = u.getRole();
		userRole.add(r);
		u.setRole(userRole);
		userRepository.save(u);

	}

	@Override
	public void deleteUser(int id) {
		this.userRepository.deleteById(id);
		
	}

}
