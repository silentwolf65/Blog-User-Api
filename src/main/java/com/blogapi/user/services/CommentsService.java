package com.blogapi.user.services;

import org.springframework.stereotype.Service;

import com.blogapi.user.payloads.CommentsDto;
@Service
public interface CommentsService {
	
	CommentsDto createComment(CommentsDto commentsDto,Integer postId);
	
	void deleteComment(Integer commentId);
}
