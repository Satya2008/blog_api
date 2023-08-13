package com.masai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masai.entities.Post;
import com.masai.exceptions.PostException;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.category.id = :categoryId")
    List<Post> findPostsByCategoryId(@Param("categoryId") int categoryId) throws PostException;

    @Query("SELECT p FROM Post p WHERE p.myUser.userId = :userId")
    List<Post> findPostsByUserId(@Param("userId") int userId) throws PostException;

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:title%")
    List<Post> searchPostByTitle(@Param("title") String title) throws PostException;

}