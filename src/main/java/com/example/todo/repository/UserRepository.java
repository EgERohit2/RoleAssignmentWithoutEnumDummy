package com.example.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.todo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByIdAndIsActiveTrue(int id);

//	@Query(value = "select name from user where name= :name",nativeQuery = true)
	User findByUsername(String name);

//	User findByUsername(String username);
	
	
//	String findByUsername(String name);
}
	