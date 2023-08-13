package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.config.AppConstant;
import com.masai.dto.PostResponse;
import com.masai.entities.Category;
import com.masai.entities.Comment;
import com.masai.entities.MyUser;
import com.masai.entities.Post;
import com.masai.exceptions.CategoryException;
import com.masai.exceptions.PostException;
import com.masai.exceptions.UserException;
import com.masai.services.CategoryService;
import com.masai.services.CommentService;
import com.masai.services.PostService;
import com.masai.services.UserService;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
	private UserService userService;
    @Autowired
	private PasswordEncoder  passwordEncoder;
    @Autowired
	private CategoryService categoryService;
    @Autowired
	private CommentService commentService;
    @Autowired
	private PostService postService;

	 @GetMapping("/signin")
		public ResponseEntity<String> logInUserHandler(Authentication auth) throws UserException{
		 
			MyUser user= userService.findByEmail(auth.getName()).get();
			 return new ResponseEntity<>(user.getEmail()+" Logged In Successfully", HttpStatus.ACCEPTED);
	 }
    
    @PostMapping("/")
   private ResponseEntity<MyUser> createUser(@Valid @RequestBody MyUser myUser){
    	myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
	   MyUser use = userService.createUser(myUser);
	   
	   return new ResponseEntity<MyUser>(use, HttpStatus.CREATED);
   } 
    @PutMapping("/{userId}")
   private ResponseEntity<MyUser> updateUser(@Valid @RequestBody MyUser myUser,  @PathVariable int userId){
	   MyUser use = userService.updateUser(myUser, userId);
	   return new ResponseEntity<MyUser>(use, HttpStatus.OK);
   } 
    
   @DeleteMapping("/{userId}")
	   private ResponseEntity<MyUser> deleteUser(@PathVariable int userId){
		   MyUser use = userService.deleteUser(userId);
		   return new ResponseEntity<MyUser>(use, HttpStatus.OK);
   } 
   @GetMapping("/{userId}")
   private ResponseEntity<MyUser> getUser(@PathVariable int userId){
	 MyUser myUser = userService.getUserById(userId);
	   return new ResponseEntity<MyUser>(myUser, HttpStatus.OK);
   }
   @GetMapping("/categories/{categoryId}")
	public ResponseEntity<Category> getCategory(@PathVariable Integer categoryId) throws CategoryException {
		Category category2 = categoryService.getCategory(categoryId);

		return new ResponseEntity<Category>(category2, HttpStatus.OK);

	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() throws CategoryException {
		List<Category> category2 = categoryService.getCategories();

		return new ResponseEntity<List<Category>>(category2, HttpStatus.OK);

	}
	@PostMapping("/{userId}/posts/{postId}")
	public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Integer userId,
			@PathVariable Integer postId) {
		Comment comm = commentService.createComment(userId, postId, comment);
		return new ResponseEntity<Comment>(comm, HttpStatus.CREATED);

	}

	@PutMapping("/comments/{commentId}")
	public ResponseEntity<Comment> updateComment(@PathVariable Integer commentId, @RequestBody Comment comment) {

		Comment updatedComment = commentService.updateComment(commentId, comment);
		return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);

	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Comment> deleteComment(@PathVariable Integer commentId) {
	
		      Comment comment =	commentService.deleteComment(commentId);
			return new ResponseEntity<Comment>(comment,HttpStatus.OK);
		
	}

	@GetMapping("/comments/post/{postId}")
	public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Integer postId) {

			List<Comment> comments = commentService.getCommentsByPost(postId);
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		
	}
	@PostMapping("/{userId}/categories/{categoryId}")
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post,  @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		Post p = postService.createPost(post, userId, categoryId);
		return new ResponseEntity<Post>(p, HttpStatus.CREATED);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post, @PathVariable Integer postId) {

		Post updatedPost = postService.updatePost(post, postId);

		return new ResponseEntity<Post>(updatedPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<Post> deletePost(@PathVariable Integer postId) {

		Post post = postService.deletePost(postId);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable Integer postId) {

		Post post = postService.getPostById(postId);
		return new ResponseEntity<Post>(post, HttpStatus.OK);

	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable int categoryId) {

		List<Post> posts = postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);

	}

	

	@GetMapping("/getAllPosts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
			) {
		PostResponse posts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyWords}")
	public ResponseEntity<List<Post>> searchPostByTitle(@PathVariable String keyWords) {

		List<Post> posts = postService.searchPostByTitle(keyWords);
		if(posts.isEmpty()) throw new PostException("No post Available by given keyWord");
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);

	}
  
}
