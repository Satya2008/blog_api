package com.masai.services;

import java.util.List;

import com.masai.entities.Comment;
import com.masai.exceptions.CommentException;

public interface CommentService {
	Comment createComment(Integer userId, Integer postId, Comment comment) throws CommentException;

	Comment updateComment(Integer commentId, Comment comment) throws CommentException;

	Comment deleteComment(Integer commentId) throws CommentException;

	List<Comment> getCommentsByPost(Integer postId) throws CommentException;

	List<Comment> getCommentsByUser(Integer userId) throws CommentException;

//	List<Comment> getLatestComments(int limit) throws CommentException;
//
//	List<Comment> getPopularComments(int limit) throws CommentException;

}
