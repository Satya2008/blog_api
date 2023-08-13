package com.masai.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masai.dto.PostResponse;
import com.masai.entities.Category;
import com.masai.entities.Post;
import com.masai.entities.MyUser;
import com.masai.exceptions.PostException;
import com.masai.repositories.CategoryRepository;
import com.masai.repositories.PostRepository;
import com.masai.repositories.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Post createPost(Post post, Integer userId, Integer categoryId) throws PostException {
		Optional<MyUser> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent())
			throw new PostException("User not found by given id " + userId);
		MyUser myUser = userOptional.get();

		Optional<Category> catOptional = categoryRepository.findById(categoryId);
		if (!catOptional.isPresent())
			throw new PostException("Category not found by given id " + categoryId);
		Category category = catOptional.get();

		post.setCategory(category);
		post.setMyUser(myUser);
		post.setImageName("default.png");
		post.setAddedDate(LocalDate.now());

		return postRepository.save(post);
	}

	@Override
	public Post updatePost(Post post, Integer postId) throws PostException {

		Optional<Post> existingPostOptional = postRepository.findById(postId);
		if (!existingPostOptional.isPresent()) {
			throw new PostException("Post not found with ID: " + postId);
		}

		Post existingPost = existingPostOptional.get();

		existingPost.setTitle(post.getTitle());
		existingPost.setContent(post.getContent());

		return postRepository.save(existingPost);
	}

	@Override
	public Post deletePost(Integer postId) throws PostException {

		Optional<Post> existingPostOptional = postRepository.findById(postId);
		if (!existingPostOptional.isPresent()) {
			throw new PostException("Post not found with ID: " + postId);
		}
		Post post = existingPostOptional.get();
		postRepository.delete(post);
		return post;
	}

	@Override
	public Post getPostById(Integer postId) throws PostException {

		Optional<Post> existingPostOptional = postRepository.findById(postId);
		if (!existingPostOptional.isPresent()) {
			throw new PostException("Post not found with ID: " + postId);
		}

		return existingPostOptional.get();
	}

	@Override
	public List<Post> getPostByCategory(int categoryId) throws PostException {
		List<Post> posts = postRepository.findPostsByCategoryId(categoryId);
		if (posts.isEmpty()) {
			throw new PostException("No posts found for the given categoryId: " + categoryId);
		}
		return posts;
	}

	@Override
	public List<Post> getPostByUser(int userId) throws PostException {
		List<Post> posts = postRepository.findPostsByUserId(userId);
		if (posts.isEmpty()) {
			throw new PostException("No posts found for the given userId: " + userId);
		}
		return posts;
	}

	@Override
	public List<Post> searchPostByTitle(String keyWord) throws PostException {
		
		List<Post> posts = postRepository.searchPostByTitle(keyWord);
		
		return posts;
     
		
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) throws PostException {
       
		Sort sort = sortDir.equalsIgnoreCase("ASC") ? sort = Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending(); 
		
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = postRepository.findAll(p);
		List<Post> posts = pagePost.getContent();
		if (posts.isEmpty()) {
			throw new PostException("No posts found ");
		}
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(posts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElememt(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

}
