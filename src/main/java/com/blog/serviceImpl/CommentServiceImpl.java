package com.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comments;
import com.blog.entities.POst;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDTO;
import com.blog.payloads.PostDTO;
import com.blog.repo.CommentRepo;
import com.blog.repo.POstRepo;
import com.blog.repo.UserRepo;
import com.blog.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private POstRepo pOstRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComments(int userid,int postid,CommentDTO dto) {
				
		User user = this.userRepo.findById(userid)
		.orElseThrow(()-> new ResourceNotFoundException("user","userid",userid));
		
		POst pOst = this.pOstRepo.findById(postid)
		.orElseThrow(()-> new ResourceNotFoundException("post","postid",postid));
		
		Comments comments =  new Comments();
		
		comments.setContent(dto.getContent());
		comments.setDate(new Date());
		comments.setUser(user);
		comments.setPost(pOst);
		
		Comments save = this.commentRepo.save(comments);

		return this.modelMapper.map(save, CommentDTO.class);
	}

	@Override
	public void deleteComment(int commentid) {
		
		Comments comments = this.commentRepo.findById(commentid)
		.orElseThrow(()-> new ResourceNotFoundException("comment","commentid",commentid));
		
		this.commentRepo.delete(comments);
	}
	

	@Override
	public List<CommentDTO> findbyPost(int postId) {
		
		POst pOst = this.pOstRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("post","postid",postId));
				
				List<Comments> list = this.commentRepo.findByPost(pOst);
				
				
				
				List<CommentDTO> list2 = list.stream().map(comment -> this.modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
				System.out.println("*****"+list2);
				return list2;
	}

}
