package com.masai.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Optional<Admin> findByEmail(String Email);
}
