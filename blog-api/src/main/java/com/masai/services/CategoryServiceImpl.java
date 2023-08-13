package com.masai.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entities.Category;
import com.masai.exceptions.CategoryException;
import com.masai.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(Category category) throws CategoryException {

		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Category category, Integer categoryId) throws CategoryException {
		Optional<Category> catOptional = categoryRepository.findById(categoryId);
		if (!catOptional.isPresent())
			throw new CategoryException("category not found by given id " + categoryId);
		Category cat = catOptional.get();
        cat.setCategoryTitle(category.getCategoryTitle());
        cat.setCategoryDescription(category.getCategoryDescription());
         
		return categoryRepository.save(cat);
	}

	@Override
	public Category deleteCategory(Integer categoryId) throws CategoryException {
		Optional<Category> catOptional = categoryRepository.findById(categoryId);
		if (!catOptional.isPresent())
			throw new CategoryException("category not found by given id " + categoryId);
		Category cat = catOptional.get();
		categoryRepository.delete(cat);
		return cat;
	}

	@Override
	public Category getCategory(Integer categoryId) throws CategoryException {
		Optional<Category> catOptional = categoryRepository.findById(categoryId);
		if (!catOptional.isPresent())
			throw new CategoryException("category not found by given id " + categoryId);
		return catOptional.get();
	}

	@Override
	public List<Category> getCategories() throws CategoryException {
		List<Category> categories = categoryRepository.findAll();
		if (categories.isEmpty())
			throw new CategoryException("No record found for category");
		return categories;
	}

}
