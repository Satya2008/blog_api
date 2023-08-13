package com.masai.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.masai.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	    List<Comment> findByPostPostId(Integer postId);
	  
	    List<Comment> findByMyUserUserId(Integer userId);

//	    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
//	    List<Comment> findLatestComments(int limit);
//
//	    @Query("SELECT c FROM Comment c ORDER BY c.upvoteCount DESC")
//	    List<Comment> findPopularComments(int limit);

}
