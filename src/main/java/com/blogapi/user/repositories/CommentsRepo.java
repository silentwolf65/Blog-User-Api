package com.blogapi.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.user.entities.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {

}
