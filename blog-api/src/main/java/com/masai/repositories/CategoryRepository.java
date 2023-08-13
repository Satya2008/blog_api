package com.masai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
