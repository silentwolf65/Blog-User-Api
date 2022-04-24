package com.blogapi.user.services;

import java.util.List;

import com.blogapi.user.entities.Post;
import com.blogapi.user.payloads.PostDto;
import com.blogapi.user.payloads.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user,Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
	
	List<PostDto> getPostsByUserId(Integer userId);
 }
