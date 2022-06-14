package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.POst;
import com.blog.entities.User;
import com.blog.payloads.PostDTO;
import com.blog.payloads.PostsREsponce;

public interface PostService {
	
	PostDTO CreatePOst(PostDTO pOst,Integer userid,Integer CategoryID);
	PostDTO updatePOst(PostDTO pOst,int id);
	PostDTO getPostById(int id);
	PostsREsponce GetAllpost(int pageNum,int pageSize,String sortby,String sortDirection);
	void deletepost(int id);
	List<PostDTO> getPostByUser(int UserID);
	List<PostDTO> getPostByCategory(int categoryId);
	List<PostDTO> getBytitleContaining(String title);
}
