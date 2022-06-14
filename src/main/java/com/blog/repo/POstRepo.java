package com.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.POst;
import com.blog.entities.User;
import com.blog.payloads.PostDTO;

public interface POstRepo extends JpaRepository<POst, Integer>{
	
	List<POst> findByUser(User user);
	List<POst> findByCategory(Category category);
	List<POst> findByTitleContaining(String Title);
}
