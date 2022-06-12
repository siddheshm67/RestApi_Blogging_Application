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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.POst;
import com.blog.payloads.ApiResponce;
import com.blog.payloads.PostDTO;
import com.blog.payloads.PostsREsponce;
import com.blog.service.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	int Categoryid2=0;
	
	@PostMapping("/user/{userid}/category/{categoryid}/post")
	public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO,
			@PathVariable("userid") int userid,
			@PathVariable("categoryid") int categoryid){
		
	 PostDTO postDTO2 = this.postService.CreatePOst(postDTO, userid, categoryid);
				
		return new ResponseEntity<PostDTO>(postDTO2,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{id}/posts")
	public ResponseEntity<List<PostDTO>> getbyuserid(@PathVariable("id") int id){
		List<PostDTO> postByUser = this.postService.getPostByUser(id);
		return new ResponseEntity<>(postByUser,HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}/posts")
	public ResponseEntity<List<PostDTO>> getByCategoryid(@PathVariable ("id") int id){
		List<PostDTO> categoryList = this.postService.getPostByCategory(id);
		return new ResponseEntity<List<PostDTO>>(categoryList,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDTO> getByid(@PathVariable ("id") int id){
		
		PostDTO dto = this.postService.getPostById(id);
		
		return new ResponseEntity<PostDTO>(dto,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostsREsponce> getall(
			@RequestParam (value = "pageNum",defaultValue = "0" ,required = false)Integer pagenum,
			@RequestParam (value = "pageSize" ,defaultValue = "3",required = false)Integer pagesize,
			@RequestParam (value = "sortby" ,defaultValue = "id",required = false)String sortby,
		@RequestParam (value = "sortDirection" ,defaultValue = "asc",required = false)String sortDirection){
		
		 PostsREsponce getAllpost = this.postService.GetAllpost(pagenum, pagesize,sortby,sortDirection);
		
		return new ResponseEntity<>(getAllpost,HttpStatus.OK);
	}
	
	@PutMapping("/post/{id}")
	public ResponseEntity<PostDTO> update(@RequestBody PostDTO dto,@PathVariable("id")int id){
		PostDTO dto2 = this.postService.updatePOst(dto, id);
		return new ResponseEntity<PostDTO>(dto2,HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{id}")
	public ResponseEntity<ApiResponce> delete(@PathVariable("id") int id){
		this.postService.deletepost(id);
		return new ResponseEntity<ApiResponce>(new ApiResponce("deleted successfully", true),HttpStatus.OK);
	}
}
