package com.masai.services;

import java.util.List;

import com.masai.entities.Category;
import com.masai.exceptions.CategoryException;

public interface CategoryService {
    
	Category createCategory(Category category) throws CategoryException;
	Category updateCategory(Category category, Integer categoryId) throws CategoryException;
	Category deleteCategory(Integer categoryId) throws CategoryException;
	Category getCategory(Integer categoryId) throws CategoryException;
	List<Category> getCategories() throws CategoryException;
}
