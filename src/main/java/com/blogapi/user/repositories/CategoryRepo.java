package com.blogapi.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapi.user.entities.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
