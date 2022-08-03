package com.blogapi.user.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapi.user.config.AppConstants;
import com.blogapi.user.config.AppProperties;
import com.blogapi.user.payloads.ApiResponse;
import com.blogapi.user.payloads.PostDto;
import com.blogapi.user.services.FileService;
import com.blogapi.user.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AppProperties appProps;
	
	@Value("${project.image}")
	private String path; 
	
	// create post
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
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
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			){
		
		List<PostDto> posts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable ("keywords") String keywords
			){
		List<PostDto> result = this.postService.searchPost(keywords);
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	
	//Post- image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			){
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
	}
	
	//method to serve file
		@GetMapping(value="post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response
				) {
			InputStream resource = this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			try {
				StreamUtils.copy(resource, response.getOutputStream());
			} catch (IOException e) {
				System.err.println("error at downloadImage method");
				e.printStackTrace();
			}
		
		}
	
	//get- fetch post by id
	@GetMapping("/posts/post/{postId}")
	public ResponseEntity<PostDto> fetchPost(
			@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse(appProps.getMessages().get(AppConstants.DEL_SUCCESS), true),HttpStatus.OK);
	}
}
