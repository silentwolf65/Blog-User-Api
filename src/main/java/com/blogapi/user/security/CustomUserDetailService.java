package com.blogapi.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogapi.user.entities.User;
import com.blogapi.user.exceptions.UserNotFoundException;
import com.blogapi.user.repositories.UserRepo;
@Service
@javax.transaction.Transactional
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load user from database by username
		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User","email",username));
		
		return user;
	}

}
