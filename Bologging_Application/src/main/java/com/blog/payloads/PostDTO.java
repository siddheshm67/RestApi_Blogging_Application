package com.blog.payloads;

import java.sql.Date;

import javax.persistence.ManyToOne;

import com.blog.entities.Category;
import com.blog.entities.POst;
import com.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {
	
	private Integer id;
	private String title;
	private String content;
	private String imgNAme;
	private Date AddedDate;
	private int updateCategoryId;
	
	private CategotyDTO category;

	private UserDTO user;
}
