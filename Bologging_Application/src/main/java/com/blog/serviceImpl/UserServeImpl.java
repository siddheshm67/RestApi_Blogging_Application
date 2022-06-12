package com.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.blog.exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.payloads.UserDTO;
import com.blog.repo.UserRepo;
import com.blog.service.UserService;


@Service
public class UserServeImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDTO CreateUser(UserDTO dto) {
		
		User user = this.DtoToUser(dto);
		
		User savedUser = this.userRepo.save(user);
		System.out.println("Controller dto : ****"+ savedUser );
		
		UserDTO dto2 = this.userToDto(savedUser);
		System.out.println("dto2id***"+dto2.getPassword());
		return dto2;
	}

	@Override
	public UserDTO UpdateUser(UserDTO userDTO, int userid) {
		
		 User user = this.userRepo.findById(userid)
				     .orElseThrow(()-> new ResourceNotFoundException("User","userid",userid));
		 
		 user.setAboutString(userDTO.getAboutString());
		 user.setEmail(userDTO.getEmail());
		 user.setName(userDTO.getName());
		 user.setPassword(userDTO.getPassword());
		 
		 this.userRepo.save(user);
		
		return userToDto(user);
	}

	@Override
	public UserDTO getUSerById(int id) {
		User user = this.userRepo.findById(id)
			     .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
		
		
		return userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUSers() {
		
		List<User> list = this.userRepo.findAll();
		
		List<UserDTO> list2 = list.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return list2;
	}

	@Override
	public void deleteUSerById(int id) {

		 User user = this.userRepo.findById(id)
				     .orElseThrow(()-> new ResourceNotFoundException("User","userid",id));
		
		 this.userRepo.delete(user);
	}
	
	private User DtoToUser(UserDTO dto) {
		
		User user = this.modelMapper.map(dto, User.class);

		return user;
	}
	
	private UserDTO userToDto(User user) {
		
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		
		return userDTO;	
	}

}
