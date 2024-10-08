package com.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.User;

public interface userRepository extends JpaRepository<User, Integer> {
	/* this user finds the user which matches its email */
	@Query("select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);
	
}
