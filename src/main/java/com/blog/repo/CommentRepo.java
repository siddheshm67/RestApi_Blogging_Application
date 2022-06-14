package com.blog.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comments;
import com.blog.entities.POst;
import com.blog.payloads.PostDTO;

public interface CommentRepo extends JpaRepository<Comments, Integer>{
	
	List<Comments> findByPost(POst pOst); 
	
}
