package com.blogapi.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.user.entities.Post;
import com.blogapi.user.payloads.PostDto;
import com.blogapi.user.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	// create post
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	// get post by user
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		List<PostDto> postsByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<>(postsByUser, HttpStatus.OK);
	}

	// get post by category
	@GetMapping("/categories/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postsByCategory = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<>(postsByCategory, HttpStatus.OK);
	}
	
	
	
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
			){
		
		List<PostDto> posts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	
	
	
}
