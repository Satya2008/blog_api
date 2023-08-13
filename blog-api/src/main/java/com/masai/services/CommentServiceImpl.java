package com.masai.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.masai.entities.Comment;
import com.masai.entities.Post;
import com.masai.entities.MyUser;
import com.masai.exceptions.CommentException;
import com.masai.repositories.CommentRepository;
import com.masai.repositories.PostRepository;
import com.masai.repositories.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public Comment createComment(Integer userId, Integer postId, Comment comment) throws CommentException {
		MyUser myUser = userRepository.findById(userId)
				.orElseThrow(() -> new CommentException("User not found with ID: " + userId));

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new CommentException("Post not found with ID: " + postId));

		comment.setMyUser(myUser);
		comment.setPost(post);

		return commentRepository.save(comment);
	}

	@Override
	public Comment updateComment(Integer commentId, Comment comment) throws CommentException {
		Comment comm = commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentException("Comment not found."));
		comm.setContent(comment.getContent());
		return commentRepository.save(comm);
	}

	@Override
	public Comment deleteComment(Integer commentId) throws CommentException {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new CommentException("Comment not found."));
		commentRepository.delete(comment);
		return comment;
	}

	@Override
	public List<Comment> getCommentsByPost(Integer postId) throws CommentException {
	postRepository.findById(postId).orElseThrow(() -> new CommentException("Post not found."));
	List<Comment> comments =  commentRepository.findByPostPostId(postId);
	if (comments.isEmpty()) throw new CommentException("No comment found in given Post");
	return comments;
	}

	@Override
	public List<Comment> getCommentsByUser(Integer userId) throws CommentException {
		userRepository.findById(userId).orElseThrow(() -> new CommentException("User not found."));
		List<Comment> comments =  commentRepository.findByPostPostId(userId);
		if (comments.isEmpty()) throw new CommentException("No comment found in given Post");
		return comments;
	}
//
//    @Override
//    public List<Comment> getLatestComments(int limit) throws CommentException {
//        return commentRepository.findLatestComments(limit);
//    }
//
//    @Override
//    public List<Comment> getPopularComments(int limit) throws CommentException {
//        return commentRepository.findPopularComments(limit);
//    }
}
