package com.blogapi.user.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapi.user.entities.Category;
import com.blogapi.user.entities.Post;
import com.blogapi.user.entities.User;
import com.blogapi.user.exceptions.ResourceNotFoundException;
import com.blogapi.user.payloads.PostDto;
import com.blogapi.user.repositories.CategoryRepo;
import com.blogapi.user.repositories.PostRepo;
import com.blogapi.user.repositories.UserRepo;
import com.blogapi.user.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		Category category =this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAdddate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		//sort condition for sorting data ascending or descending
		Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		//conditions for pagination
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		//fetching record with pagination
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> content = pagePost.getContent();
		return content.stream().map(post->
						this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->
				new ResourceNotFoundException("User", "userId", userId));
		return this.postRepo.findByUser(user).stream().map(post->this.modelMapper.map(post, PostDto.class)).sorted().collect(Collectors.toList());
		
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> 
		new ResourceNotFoundException("category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		return posts.stream().map(post-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> listOfPost = this.postRepo.findByTitle("%"+keyword+"%");
		return listOfPost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
	}
	
}
