package com.blogapi.user.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blogapi.user.exceptions.ResourceNotFoundException;
import com.blogapi.user.payloads.UserDTO;
import com.blogapi.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = UserController.class)
public class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	private UserService userService;
	
	UserDTO userdto1 = new UserDTO(1,"John","johnwick@hightable.com","dogs","its john wick");
	UserDTO userdto2= new UserDTO(2,"Robert","ironman@avengers.com","itstony","im iron man");
	UserDTO userdto3 = new UserDTO(3,"Thor","thundergod@asguard.com","stormbreaker","God of thunder");
	
	@Test
	public void getAllUsers_success()throws Exception{
		List<UserDTO> userList = new ArrayList<>(Arrays.asList(userdto1,userdto2,userdto3));
		
		Mockito.when(userService.getAllUsers()).thenReturn(userList);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/users/allUsers")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[2].name", is("Thor")));
	}
	
	@Test
	public void getUser_success()throws Exception{
		Mockito.when(userService.getUserById(userdto1.getId())).thenReturn(userdto1);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/users/1")
				.contentType(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("John")));
	}
	
	@Test
	public void getUser_ResourceNotFound() throws Exception{
		Mockito.when(userService.getUserById(4)).thenThrow(new ResourceNotFoundException("User","id",4));
		
		//MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/users/4");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/users/4")
				.contentType(MediaType.ALL))
				.andExpect(status().isNotFound())
				.andExpect(result->
					assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
				
	}
	
}
