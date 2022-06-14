package com.blog.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.APP_CONSTANT;
import com.blog.entities.POst;
import com.blog.payloads.ApiResponce;
import com.blog.payloads.PostDTO;
import com.blog.payloads.PostsREsponce;
import com.blog.service.FileService;
import com.blog.service.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
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
			@RequestParam (value = "pageNum",defaultValue = APP_CONSTANT.PAGE_NUMBER ,required = false)Integer pagenum,
			@RequestParam (value = "pageSize" ,defaultValue = APP_CONSTANT.PAGE_SIZE,required = false)Integer pagesize,
			@RequestParam (value = "sortby" ,defaultValue = APP_CONSTANT.SORT_BY,required = false)String sortby,
		@RequestParam (value = "sortDirection" ,defaultValue = APP_CONSTANT.SORT_DIRECTION,required = false)String sortDirection){
		
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
	
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable ("keywords")String keyword){
		List<PostDTO> bytitleContaining = this.postService.getBytitleContaining(keyword);
		return new ResponseEntity<List<PostDTO>>(bytitleContaining,HttpStatus.OK);
	}
	
	@PostMapping("/post/upload/{postId}")
	public ResponseEntity<PostDTO> uploadimg(@RequestParam("img")MultipartFile imgfile,@PathVariable int postId) throws IOException{
		
		PostDTO postById = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadFile(path, imgfile);
		postById.setImgNAme(fileName);
		PostDTO postDTO = this.postService.updatePOst(postById, postId);
		
		return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/post/img/{imgname}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImg(@PathVariable String imgname,HttpServletResponse response) throws IOException {
		 InputStream stream = this.fileService.getresouce(path, imgname);
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 org.hibernate.engine.jdbc.StreamUtils.copy(stream, response.getOutputStream());
	}
}
