package com.smart.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;

public interface contactRepository extends JpaRepository<Contact, Integer> {

	//pegination..
	
	@Query("from Contact as c where c.user.id=:userId")
	// currentPage page
	// contact per page- 5
	public Page<Contact> findContactByUser(@Param("userId") int userId, PageRequest pageable);
	
}
