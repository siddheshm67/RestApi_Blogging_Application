package com.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategotyDTO;
import com.blog.repo.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategotyDTO createCategory(CategotyDTO dto) {
		
		Category category = this.DtoToCategory(dto);
		
		Category save = this.categoryRepo.save(category);
		
		return this.categotyToDto(save);
	}

	@Override
	public CategotyDTO updateCategory(CategotyDTO dto, int id) {
		
		Category category = this.DtoToCategory(dto);
		
		Category category2 = this.categoryRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("User","userid",id));
		
		category2.setTitle(category.getTitle());
		category2.setDescription(category.getDescription());
		
		Category save = this.categoryRepo.save(category2);
		
		return this.categotyToDto(save);
	}

	@Override
	public CategotyDTO getCategoryBYId(int id) {

		Category category2 = this.categoryRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("User","userid",id));
		
		return this.categotyToDto(category2);
	}

	@Override
	public List<CategotyDTO> getAllCategories() {
		
		List<Category> all = this.categoryRepo.findAll();
		
		List<CategotyDTO> list = all.stream().map(cate -> this.categotyToDto(cate)).collect(Collectors.toList());
		
		return list;
	}

	@Override
	public void deleteCategory(int id) {
		
		Category category2 = this.categoryRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User","userid",id));
		
		this.categoryRepo.delete(category2);
	}
	
	private CategotyDTO categotyToDto(Category category) {
		CategotyDTO dto = this.modelMapper.map(category, CategotyDTO.class);
		return dto;
	}
	
	private Category DtoToCategory(CategotyDTO dto) {
		 Category category = this.modelMapper.map(dto, Category.class);
		return category;
	}
	
	

}
