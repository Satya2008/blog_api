package com.masai.exceptions;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> handleUserException(UserException ex, WebRequest wr){
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		myErrorDetails.setMessage(ex.getMessage());
		myErrorDetails.setDiscription(wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorDetails> handleAdminException(AdminException ex, WebRequest wr){
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		myErrorDetails.setMessage(ex.getMessage());
		myErrorDetails.setDiscription(wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<MyErrorDetails> handleCategoryException(CategoryException ex, WebRequest wr){
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		myErrorDetails.setMessage(ex.getMessage());
		myErrorDetails.setDiscription(wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PostException.class)
	public ResponseEntity<MyErrorDetails> handlePostException(PostException ex, WebRequest wr){
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		myErrorDetails.setMessage(ex.getMessage());
		myErrorDetails.setDiscription(wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CommentException.class)
	public ResponseEntity<MyErrorDetails> handlePostException(CommentException ex, WebRequest wr){
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		myErrorDetails.setMessage(ex.getMessage());
		myErrorDetails.setDiscription(wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> handleValidationeException(MethodArgumentNotValidException ex, WebRequest wr){
//		create an object of my error entity
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		
		myErrorDetails.setMessage("Validation failed !!");
//		myErrorDetails.setDiscription("");
//		 list of all error object is here
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//		 list of all error messages is here
		List<String> errorMessages = MethodArgumentNotValidException.errorsToStringList(allErrors);
		
//		set the details here
		myErrorDetails.setDiscription(String.join(", ", errorMessages));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> Exception(Exception ex, WebRequest wr){
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		myErrorDetails.setTimeStamp(LocalDate.now());
		myErrorDetails.setMessage(ex.getMessage());
		myErrorDetails.setDiscription(wr.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(myErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
}
