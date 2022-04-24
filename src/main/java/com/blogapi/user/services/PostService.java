package com.blogapi.user.services;

import java.util.List;

import com.blogapi.user.entities.Post;
import com.blogapi.user.payloads.PostDto;

public interface PostService{
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete post 
	void deletePost(Integer postId);
	
	//get all posts
	List<PostDto> getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);
	
	//get post by id
	PostDto getPostById(Integer postId);
	
	//get posts by users
	List<PostDto> getPostByUser(Integer userId);
	
	//get posts by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//search Post
	List<PostDto> searchPost(String keyword);
}
