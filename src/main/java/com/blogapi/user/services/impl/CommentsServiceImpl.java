package com.blogapi.user.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.user.config.AppConstants;
import com.blogapi.user.entities.Comments;
import com.blogapi.user.entities.Post;
import com.blogapi.user.exceptions.ResourceNotFoundException;
import com.blogapi.user.payloads.CommentsDto;
import com.blogapi.user.repositories.CommentsRepo;
import com.blogapi.user.repositories.PostRepo;
import com.blogapi.user.services.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService{
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentsRepo commentsRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentsDto createComment(CommentsDto commentsDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));
		Comments comment = this.modelMapper.map(commentsDto, Comments.class);
		
		comment.setPost(post);
		Comments savedComment = this.commentsRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentsDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments comment = this.commentsRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.COMMENT, AppConstants.COMMENT_ID, commentId));
		this.commentsRepo.delete(comment);
	}
	
}
