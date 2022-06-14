package com.blog.service;

import java.util.List;

import com.blog.entities.Comments;
import com.blog.payloads.CommentDTO;

public interface CommentService {
	
	CommentDTO createComments(int userid,int postid,CommentDTO dto);
	void deleteComment(int commentid);
	List<CommentDTO>findbyPost(int postId);
	
}
