package com.blogapi.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.user.entities.User;
import com.blogapi.user.exceptions.ResourceNotFoundException;
import com.blogapi.user.payloads.UserDTO;
import com.blogapi.user.repositories.UserRepo;
import com.blogapi.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Create New User Method
	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
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
		return this.userToDto(updatedUser);
	}

	//Find User Method based on Id
	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		return this.userToDto(user);
	}

	//Find All The Available User Records 
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		//converting and returning list of users
		return users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
	}

	//Delete User By Id
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		this.userRepo.delete(user);
	}
	
	//ModelMapper is easier way to convert one object to another
	private User dtoToUser(UserDTO userDto) {
		//returning converted object (userDto -> user)
		return this.modelMapper.map(userDto, User.class);
	}
	
	//ModelMapper is easier way to convert one object to another
	private UserDTO userToDto(User user) {
		//returning converted object (userDto -> user)
		return this.modelMapper.map(user, UserDTO.class);
	}

}
