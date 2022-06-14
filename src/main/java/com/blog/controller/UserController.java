package com.blog.controller;

import java.util.List;
import javax.validation.Valid;

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
import com.blog.payloads.UserDTO;
import com.blog.serviceImpl.UserServeImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServeImpl userServeImpl;
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createuser(@Valid @RequestBody UserDTO dto){
		
		UserDTO dto2 = this.userServeImpl.CreateUser(dto);
		
		System.out.println("Controller dto : ****"+dto2);
		
		return new ResponseEntity<>(dto2,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> upadateuser(@Valid @RequestBody UserDTO dto,@PathVariable("id") int id){
		
		UserDTO userDTO = this.userServeImpl.UpdateUser(dto, id);
		
		return new ResponseEntity<>(userDTO,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponce>deleteuser(@PathVariable("id") int id){
		
		this.userServeImpl.deleteUSerById(id);
		
		return new ResponseEntity<>(new ApiResponce("user deleted successfully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserbyid(@PathVariable ("id") int id){
			
		UserDTO uSerById = this.userServeImpl.getUSerById(id);
		
		return new ResponseEntity<UserDTO>(uSerById,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getallUser(){
		List<UserDTO> allUSers = this.userServeImpl.getAllUSers();
		
		return new ResponseEntity<>(allUSers,HttpStatus.OK);
	}
	
	
	
}
