package com.blogapi.user.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Integer postId;
	
	private String title;

	private String content;

	private String imageName;

	private Date addDate;

	private CategoryDto category;

	private UserDTO user;
	
	private Set<CommentsDto> comments = new HashSet<>(); 

	/*
	 * @Override public int compareTo(PostDto o) { return
	 * Comparator.comparing(PostDto::getPostId).compare(this, o);
	 * 
	 * }
	 */
}
