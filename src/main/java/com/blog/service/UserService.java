package com.blog.service;

import java.util.List;

import com.blog.payloads.UserDTO;


public interface UserService {
	
	UserDTO CreateUser(UserDTO dto);
	UserDTO UpdateUser(UserDTO userDTO,int id);
	UserDTO getUSerById(int id);
	List<UserDTO> getAllUSers();
	void deleteUSerById(int id);
	
}
