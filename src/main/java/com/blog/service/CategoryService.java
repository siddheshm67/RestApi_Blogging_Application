package com.blog.service;

import java.util.List;

import com.blog.payloads.CategotyDTO;

public interface CategoryService {
	
	CategotyDTO createCategory(CategotyDTO dto);
	CategotyDTO updateCategory(CategotyDTO dto,int id);
	CategotyDTO getCategoryBYId(int id);
	List<CategotyDTO> getAllCategories();
	void deleteCategory(int id);
	
	
}
