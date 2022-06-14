package com.blog.payloads;

import java.util.Date;

import javax.persistence.ManyToOne;

import com.blog.entities.POst;
import com.blog.entities.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentDTO {
	
	private int id;
	private String content;
	private Date date;
	
	private PostDTO pOst;
	
	private UserDTO user;
}
