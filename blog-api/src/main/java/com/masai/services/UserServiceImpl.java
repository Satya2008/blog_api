package com.masai.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entities.MyUser;
import com.masai.exceptions.UserException;
import com.masai.repositories.UserRepository;
@Service
public class UserServiceImpl implements UserService {
     
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public MyUser createUser(MyUser myUser) {
		
		return userRepository.save(myUser);
	}

	@Override
	public MyUser updateUser(MyUser myUser, Integer userId) {
	    MyUser us  = userRepository.findById(userId)
	    		.orElseThrow(()-> new UserException("User not found by given userId" + userId));
	    
	    us.setName(myUser.getName());
	    us.setEmail(myUser.getEmail());
	    us.setAbout(myUser.getAbout());
		return userRepository.save(us);
	
	}

	@Override
	public MyUser getUserById(Integer userId) {
		Optional<MyUser> myUser = userRepository.findById(userId);
		if(!myUser.isPresent()) throw new UserException("User not found by given id "+ userId);
		MyUser us = myUser.get();
		return us;
	}

	

	@Override
	public MyUser deleteUser(Integer userId) {
	    Optional<MyUser> optionalUser = userRepository.findById(userId);
	    if (optionalUser.isPresent()) {
	        MyUser myUser = optionalUser.get();
	        userRepository.delete(myUser);
	        return myUser;
	    } else {
	        throw new UserException("User not found for the given id: " + userId);
	    }
	}

	@Override
	public Optional<MyUser> findByEmail(String Email) {
	
			Optional<MyUser> user= userRepository.findByEmail(Email);
			 if(user.isEmpty()) throw new UserException("No user found");
			 return user;
		}
		
	}

