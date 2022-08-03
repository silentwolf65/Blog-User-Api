package com.blogapi.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.user.config.AppConstants;
import com.blogapi.user.config.AppProperties;
import com.blogapi.user.payloads.ApiResponse;
import com.blogapi.user.payloads.CommentsDto;
import com.blogapi.user.services.CommentsService;

@RestController
@RequestMapping("/api/")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private AppProperties appProps;
	
	//Post -Comment
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentsDto> createComment(
			@RequestBody CommentsDto commentsDto,
			@PathVariable Integer postId){
		
		CommentsDto createdComment = this.commentsService.createComment(commentsDto, postId);
		
		return new ResponseEntity<>(createdComment,HttpStatus.CREATED);
	}
	
	//Delete- Comment
	@DeleteMapping("/comments/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable Integer commentId
			){
		this.commentsService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(appProps.getMessages().get(AppConstants.DEL_SUCCESS),true),HttpStatus.OK);
	}
}
