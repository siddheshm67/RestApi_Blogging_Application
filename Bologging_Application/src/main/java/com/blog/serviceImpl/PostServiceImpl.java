 package com.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.POst;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDTO;
import com.blog.payloads.PostsREsponce;
import com.blog.repo.CategoryRepo;
import com.blog.repo.POstRepo;
import com.blog.repo.UserRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private POstRepo pOstRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDTO CreatePOst(PostDTO dto,Integer userid,Integer categoryid) {
		
		 User user = this.userRepo.findById(userid)
			     .orElseThrow(()-> new ResourceNotFoundException("User","userid",userid));
		 
		 Category category2 = this.categoryRepo.findById(categoryid)
					.orElseThrow(()-> new ResourceNotFoundException("User","userid",categoryid)); 
		
		POst pOst = this.DtoToPOst(dto);
		
		pOst.setImgNAme("default.png");
		pOst.setAddedDate(new Date());
		pOst.setUser(user);
		pOst.setCategory(category2);
		pOst.setUpdateCategoryId(category2.getId());
		
		POst save = this.pOstRepo.save(pOst);
		
		return postToDto(save);
	}

	@Override
	public PostDTO updatePOst(PostDTO dto, int id) {
		
		Category category2=null;
		
		POst pOst = this.pOstRepo.findById(id)
				 .orElseThrow(()-> new ResourceNotFoundException("post","postId",id));
		
		System.out.println("dto.getUpdateCategoryId()***"+dto.getUpdateCategoryId());
		
		if (dto.getUpdateCategoryId()==0) {
			
		 category2 = this.categoryRepo.findById(pOst.getUpdateCategoryId())
				.orElseThrow(()-> new ResourceNotFoundException("Category","Category",pOst.getUpdateCategoryId()));
		 System.out.println("zero "+category2);
		}
		
		if (dto.getUpdateCategoryId()!=0) {
			category2 = this.categoryRepo.findById(dto.getUpdateCategoryId())
					.orElseThrow(()-> new ResourceNotFoundException("Category","Category",dto.getUpdateCategoryId()));
			 System.out.println("not zero"+category2);
		}

		
		pOst.setTitle(dto.getTitle());
		pOst.setContent(dto.getContent());
		pOst.setImgNAme(dto.getImgNAme());
		pOst.setUpdateCategoryId(category2.getId());
		pOst.setCategory(category2);
	
		
		POst save = this.pOstRepo.save(pOst);
		
		return this.postToDto(save);
	}

	@Override
	public PostDTO getPostById(int id) {

	 POst pOst = this.pOstRepo.findById(id)
	 .orElseThrow(()-> new ResourceNotFoundException("post","postId",id));
	 	
		return this.postToDto(pOst);
	}

	@Override
	public PostsREsponce GetAllpost(int pageNum,int pageSize,String sortby,String sortDirection) {
		
		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortby).ascending();
		}else {
			sort=Sort.by(sortby).descending();
		}
		
		org.springframework.data.domain.Pageable p = PageRequest.of(pageNum, pageSize,sort);
		
		 Page<POst> page = this.pOstRepo.findAll(p);
		 
		 List<POst> list = page.getContent();
		
		List<PostDTO> list2 = list.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
		
		PostsREsponce postsREsponce = new PostsREsponce();
		
		postsREsponce.setContentDtos(list2);
		postsREsponce.setPageNo(page.getNumber());
		postsREsponce.setPageSize(page.getSize());
		postsREsponce.setTotalElements(page.getTotalElements());
		postsREsponce.setTotalPages(page.getTotalPages());
		postsREsponce.setLastPage(page.isLast());
		
		return postsREsponce;
	}

	@Override
	public void deletepost(int id) {
		
		POst pOst = this.pOstRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("post","POst id",id));
		
		this.pOstRepo.delete(pOst);
	}

	@Override
	public List<PostDTO> getPostByUser(int UserID) {
		User user = this.userRepo.findById(UserID)
		.orElseThrow(()-> new ResourceNotFoundException("User","userid",UserID));
		
		List<POst> findByUserlist = this.pOstRepo.findByUser(user);
		
		List<PostDTO> list2 = findByUserlist.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
		
		return list2;
	}

	@Override
	public List<PostDTO> getPostByCategory(int categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category","Category id ",categoryId));
		
		List<POst> list = pOstRepo.findByCategory(category);
		
		List<PostDTO> list2 = list.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
		
		return list2;
	}
	
	public POst DtoToPOst(PostDTO postDTO) {
		POst post = this.modelMapper.map(postDTO, POst.class);
		return post;
	}
	
	public PostDTO postToDto(POst pOst) {
		PostDTO dto = this.modelMapper.map(pOst, PostDTO.class);
		return dto;
	}

}
