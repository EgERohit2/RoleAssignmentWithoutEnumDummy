package com.example.todo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.todo.dto.TaskDto;
import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserTaskHistoryRepository;
import com.example.todo.repository.UserTaskRepository;
import com.example.todo.service.TaskService;

@Service
public class TaskServiceImplementation implements TaskService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserTaskHistoryServiceImplementation userTaskHistoryServiceImplementation;

	@Autowired
	private UserTaskServiceImplementation userTaskServiceImplementation;

	@Autowired
	private UserTaskRepository userTaskRepository;

	@Autowired
	private UserTaskHistoryRepository userTaskHistoryRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task saveTask(Task task) {
		// TODO Auto-generated method stub
		return taskRepository.save(task);
	}

	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return taskRepository.findAll();
	}

	@Override
	public Optional<Task> getTaskById(int id) {
		// TODO Auto-generated method stub
		return taskRepository.findById(id);
	}

	@Override
	public Task updateTask(int id, Task task) {
		// TODO Auto-generated method stub
		Optional<Task> optionalTask = taskRepository.findById(id);
		if (optionalTask.isPresent()) {
			Task existingTask = optionalTask.get();
			existingTask.setName(task.getName());
			existingTask.setDesc(task.getDesc());
			existingTask.setStartDate(task.getStartDate());
			existingTask.setEndDate(task.getEndDate());
			existingTask.setUsertask(task.getUsertask());
			return taskRepository.save(existingTask);
		} else {
			return null;
		}
	}
//
//	@Override
//	@Scheduled(cron = "0 0 * * * *")
//	public void checkOverdueTasks() {
//		// TODO Auto-generated method stub
//		List<Task> tasks = taskRepository.findAll();
//		for (Task t1 : tasks) {
//			if (t1.getEndDate().before(new Date())) {
//				t1.setStatus(TaskStatus.OVERDUE);
//				taskRepository.save(tasks);
//			}
//		}
//
//	}

	@Override
	public TaskDto converEntitytoDto(Task task) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		TaskDto taskDto = new TaskDto();
		taskDto = modelMapper.map(task, TaskDto.class);

		return taskDto;
	}

	@Override
	public List<TaskDto> getAllTaskDto() {
		// TODO Auto-generated method stub
		return taskRepository.findAll().stream().map(this::converEntitytoDto).collect(Collectors.toList());
	}

	@Override
	public Optional<TaskDto> getTaskDtoById(int id) {
		// TODO Auto-generated method stub
		Task task = taskRepository.findById(id).get();
		TaskDto taskDto = modelMapper.map(task, TaskDto.class);
		return Optional.of(taskDto);
	}

	@Override
	public Page<TasksDto> getAllTaskDto(String search,String pageNumber,String pageSize) {
		Pageable paging = PageRequest.of(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize));
		Page<TasksDto> cvList;
		if ((search == "") || (search == null) || (search.length() == 0)) {
			cvList = taskRepository.findByOrderById(paging, TasksDto.class);
		} else {
			cvList = taskRepository.findByNameContainingIgnoreCaseOrderById(StringUtils.trimAllWhitespace(search),
					paging, TasksDto.class); 
		}

		return cvList;
		
	}

	@Override
	public Page<TaskDto> getAllwithDto(int pageNumber, int pageSize) {
		List<Task> taskList = taskRepository.findAll();
	    List<TaskDto> taskDtos = new ArrayList<>();

	    ModelMapper modelMapper = new ModelMapper(); // create an instance of ModelMapper

	    for (Task task : taskList) {
	        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
	        taskDtos.add(taskDto);
	    }

	    return new PageImpl<>(taskDtos, PageRequest.of(pageNumber, pageSize), taskDtos.size());
	}

	

//	@Override
//	public Page<TaskDto> getAllTaskDtoPagination(String search, String pageNumber, String pageSize) {
//		Pageable paging = PageRequest.of(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize));
//		Page<TaskDto> cvList;
//		if ((search == "") || (search == null) || (search.length() == 0)) {
//			cvList = taskRepository.findByOrderById(paging, TaskDto.class);
//		} else {
//			cvList = taskRepository.findBytask_nameContainingIgnoreCaseOrderById(StringUtils.trimAllWhitespace(search),
//					paging, TaskDto.class);
//		}
//
//		return cvList;
//	}
//}
//	@Override
//	public void checkOverdueTasks() {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void checkOverdueTasksAndUpdateStatus() {
//		// TODO Auto-generated method stub
//		 List<Task> overdueTasks = taskRepository.findByEndDateBeforeAndStatusNot(new Date(), TaskStatus.OVERDUE);
//	        
//	        for (Task task : overdueTasks) {
//	            task.setStatus(TaskStatus.OVERDUE);
//	            taskRepository.save(task);
//	            userTaskServiceImplementation.updateUserTask(task.getId(), TaskStatus.OVERDUE);
//	            userTaskServiceImplementation.createTaskHistory(task.getId(), TaskStatus.OVERDUE);
//	        }
//	    }

//@Override
//public UserDto converEntitytoDto(User user) {
//	// TODO Auto-generated method stub
//	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//	UserDto userDto = new UserDto();
//	userDto = modelMapper.map(user, UserDto.class);
//
//	return userDto;
//}
//
//@Override
//public List<UserDto> getAllUserDto() {
//	// TODO Auto-generated method stub
//	return userRepository.findAll().stream().map(this::converEntitytoDto).collect(Collectors.toList());
//}
}