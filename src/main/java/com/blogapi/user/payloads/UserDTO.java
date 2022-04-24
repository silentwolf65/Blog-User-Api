package com.blogapi.user.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDTO implements Comparable<UserDTO>{
	
	private Integer id;
	
	@NotEmpty
	@Size(min = 3,message="UserName must be minimum 3 characters")
	private String name;
	
	@Email(message="Email address is not valid !!")
	private String email;
	
	@NotEmpty
	@Size(min=4,max=10,message="Password must be minimum 4 chars and maximum 10 chars !!")
	private String password;
	
	@NotEmpty
	private String about;

	@Override
	public int compareTo(UserDTO o) {
		
		return Comparator.comparingInt(UserDTO::getId).compare(this, o);
	}
}
