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
import org.springframework.web.bind.annotation.RestController;

import com.masai.entities.Admin;
import com.masai.entities.Category;
import com.masai.entities.Comment;
import com.masai.entities.MyUser;
import com.masai.entities.Post;
import com.masai.exceptions.CategoryException;
import com.masai.exceptions.UserException;
import com.masai.services.AdminService;
import com.masai.services.CategoryService;
import com.masai.services.CommentService;
import com.masai.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;

	@GetMapping("/signin")
	public ResponseEntity<String> logInUserHandler(Authentication auth) throws UserException {
		Admin admin = adminService.findByEmail(auth.getName()).get();
		return new ResponseEntity<>(admin.getEmail() + " Logged In Successfully", HttpStatus.ACCEPTED);
	}

	@PostMapping("/")
	private ResponseEntity<Admin> createUser(@Valid @RequestBody Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		Admin adm = adminService.createAdmin(admin);

		return new ResponseEntity<Admin>(adm, HttpStatus.CREATED);
	}

	@PutMapping("/{adminId}")
	private ResponseEntity<Admin> updateAdmin(@Valid @RequestBody Admin admin, @PathVariable int adminId) {
		Admin adm = adminService.updateAdmin(admin, adminId);
		return new ResponseEntity<Admin>(adm, HttpStatus.OK);
	}

	@DeleteMapping("/{adminId}")
	private ResponseEntity<Admin> deleteAdmin(@PathVariable int adminId) {
	Admin	adm = adminService.deleteAdmin(adminId);
		return new ResponseEntity<Admin>(adm, HttpStatus.OK);
	}

	@GetMapping("/{adminId}")
	private ResponseEntity<Admin> getAdmin(@PathVariable int adminId) {
		Admin admin = adminService.getAdminById(adminId);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@GetMapping("/AllUsers")
	private ResponseEntity<List<MyUser>> getAllUsers() {
		List<MyUser> use = adminService.getAllUsers();
		if (use == null)
			throw new UserException("No User found ");
		return new ResponseEntity<List<MyUser>>(use, HttpStatus.OK);
	}
	@PostMapping("/categories")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws CategoryException {
		Category category2 = categoryService.createCategory(category);

		return new ResponseEntity<Category>(category2, HttpStatus.CREATED);

	}
	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category, @PathVariable Integer categoryId)
			throws CategoryException {
		Category category2 = categoryService.updateCategory(category, categoryId);

		return new ResponseEntity<Category>(category2, HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<Category> deleteCategory(@PathVariable Integer categoryId) throws CategoryException {
		Category category2 = categoryService.deleteCategory(categoryId);

		return new ResponseEntity<Category>(category2, HttpStatus.OK);

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
	@GetMapping("/comments/user/{userId}")
	public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Integer userId) {
	
			List<Comment> comments = commentService.getCommentsByUser(userId);
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		
	}
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<Post>> getPostsByUser(@PathVariable int userId) {

		List<Post> posts = postService.getPostByUser(userId);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

}
