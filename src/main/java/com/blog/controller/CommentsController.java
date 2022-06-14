package com.blog.controller;

import java.util.List;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponce;
import com.blog.payloads.CommentDTO;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentsController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{userid}/post/{postid}/comment")
	public ResponseEntity<CommentDTO>Add(
			@RequestBody CommentDTO commentDTO,
			@PathVariable int userid,
			@PathVariable int postid){
		
		CommentDTO comments = this.commentService.createComments(userid, postid, commentDTO);
		
		return new ResponseEntity<CommentDTO> (comments,HttpStatus.OK);
	}
	
	@GetMapping("/post/{postid}/comment")
	public ResponseEntity<List<CommentDTO>> getByPost(@PathVariable int postid){
		List<CommentDTO> findbyPost = this.commentService.findbyPost(postid);
		return new ResponseEntity<>(findbyPost, HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/{Commentid}")
	public ResponseEntity<ApiResponce> delete(@PathVariable int Commentid){
		
		this.commentService.deleteComment(Commentid);
		
		return new ResponseEntity<ApiResponce>(new ApiResponce("deleted successfully", true),HttpStatus.OK);
	}
}
