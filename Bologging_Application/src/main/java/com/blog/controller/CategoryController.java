package com.blog.controller;

import java.util.List;

import javax.validation.Valid;
import org.aspectj.weaver.NewParentTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponce;
import com.blog.payloads.CategotyDTO;
import com.blog.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategotyDTO> create(@Valid @RequestBody CategotyDTO dto) { 
		CategotyDTO category = this.categoryService.createCategory(dto);
		
		return new ResponseEntity<CategotyDTO>(category,HttpStatus.CREATED);
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<CategotyDTO>update(@Valid @RequestBody CategotyDTO dto, @PathVariable("id") int id){
		CategotyDTO updateCategory = this.categoryService.updateCategory(dto, id);
		return new ResponseEntity<CategotyDTO>(updateCategory,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategotyDTO> getbyid(@PathVariable("id") int id){
		CategotyDTO categotyDTO = this.categoryService.getCategoryBYId(id);
		return new ResponseEntity<CategotyDTO> (categotyDTO,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategotyDTO>> getALL(){
		List<CategotyDTO> list = this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategotyDTO>>(list,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponce> delete(@PathVariable("id") int id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponce>(new ApiResponce("deleted successfully",true),HttpStatus.OK);
	}
}
