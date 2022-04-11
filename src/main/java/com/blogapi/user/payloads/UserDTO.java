package com.blogapi.user.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDTO {
	
	private Integer id;
	private String name;
	private String email;
	private String password;
	private String about;
}
