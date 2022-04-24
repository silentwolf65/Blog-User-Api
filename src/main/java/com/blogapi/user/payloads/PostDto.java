package com.blogapi.user.payloads;

import java.util.Comparator;
import java.util.Date;

import com.blogapi.user.entities.Category;
import com.blogapi.user.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDto{
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addDate;
	
	private CategoryDto category;
	
	private UserDTO user;

	/*
	 * @Override public int compareTo(PostDto o) { return
	 * Comparator.comparing(PostDto::getPostId).compare(this, o);
	 * 
	 * }
	 */
}
