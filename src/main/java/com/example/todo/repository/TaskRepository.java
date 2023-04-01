package com.example.todo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.todo.dto.TasksDto;
import com.example.todo.entities.Task;
import com.example.todo.entities.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	void save(List<Task> tasks);

//	List<Task> findByEndDateBeforeAndStatusNot(Date date, TaskStatus overdue);

	Page<TasksDto> findByNameContainingIgnoreCaseOrderById(String trimAllWhitespace, Pageable paging,
			Class<TasksDto> class1);

	Page<TasksDto> findByOrderById(Pageable paging, Class<TasksDto> class1);

//	Page<TaskDto> findBytask_nameContainingIgnoreCaseOrderById(String trimAllWhitespace, Pageable paging, 
//			Class<TaskDto> class1);

	// 31 march 2023

//	public List<Task> findByStatusAndStartDateAndEndDate(TaskStatus status, Date startDate, Date endDate);

	@Query(value = "SELECT * FROM Task t " + "WHERE t.task_name LIKE %:search% " + "OR t.start_date LIKE %:search% "
			+ "OR t.end_date LIKE %:search% ", nativeQuery = true)
	List<Task> findBySearch(@Param("search") String search);

}
