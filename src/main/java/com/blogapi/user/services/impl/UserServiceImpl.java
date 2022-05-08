package com.blogapi.user.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.user.entities.Post;
import com.blogapi.user.entities.User;
import com.blogapi.user.exceptions.ResourceNotFoundException;
import com.blogapi.user.payloads.PostDto;
import com.blogapi.user.payloads.UserDTO;
import com.blogapi.user.repositories.PostRepo;
import com.blogapi.user.repositories.UserRepo;
import com.blogapi.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepo postRepo;
	
	// Create New User Method
	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		User savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDTO.class);
	}

	//Update User Information Method
	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.modelMapper.map(updatedUser,UserDTO.class);
	}

	//Find User based on Id
	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		return this.modelMapper.map(user, UserDTO.class);
	}

	//Find All The Available User Records 
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		//converting and returning list of users
		return users.stream().map(user-> this.modelMapper.map(user,UserDTO.class)).sorted().collect(Collectors.toList());
		
	}

	//Delete User By Id
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		this.userRepo.delete(user);
	}
	
	/*
	 * //ModelMapper is easier way to convert one object to another private User
	 * dtoToUser(UserDTO userDto) { //returning converted object (userDto -> user)
	 * return this.modelMapper.map(userDto, User.class); }
	 * 
	 * //ModelMapper is easier way to convert one object to another private UserDTO
	 * userToDto(User user) { //returning converted object (userDto -> user) return
	 * this.modelMapper.map(user, UserDTO.class); }
	 */

	
	
	@Override
	public List<PostDto> getPostsByUserId(Integer userId) {
		
	User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
		List<Post> userPosts = this.postRepo.findByUser(user);
		return userPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
	}

}
