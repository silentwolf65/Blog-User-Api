package com.blogapi.user.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.user.config.AppConstants;
import com.blogapi.user.entities.Category;
import com.blogapi.user.exceptions.ResourceNotFoundException;
import com.blogapi.user.payloads.CategoryDto;
import com.blogapi.user.repositories.CategoryRepo;
import com.blogapi.user.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	//Add New Category 
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		//Category addedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(this.categoryRepo.save(category), CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		//Category updatedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(this.categoryRepo.save(category), CategoryDto.class);
	}
	
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
		.orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> catagories = this.categoryRepo.findAll();
		return catagories.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).sorted().collect(Collectors.toList());
	}

}
