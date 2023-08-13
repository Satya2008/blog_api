package com.masai.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.entities.Admin;
import com.masai.entities.MyUser;

public interface UserRepository extends JpaRepository<MyUser, Integer> {
	Optional<MyUser> findByEmail(String Email);

	
}
