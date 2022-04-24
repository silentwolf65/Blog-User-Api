package com.blogapi.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapi.user.entities.Category;
import com.blogapi.user.entities.Post;
import com.blogapi.user.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);

	@Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(:key)")
	List<Post> findByTitle(@Param("key") String title);
}
