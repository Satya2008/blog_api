package com.masai.services;

import java.util.List;
import java.util.Optional;

import com.masai.entities.Admin;
import com.masai.entities.MyUser;
import com.masai.exceptions.AdminException;

public interface AdminService {
   Admin createAdmin(Admin admin) throws AdminException;
   List<MyUser> getAllUsers() throws AdminException;
	Admin updateAdmin(Admin Admin, Integer AdminId) throws AdminException;
	Admin getAdminById(Integer AdminId) throws AdminException;
	Admin deleteAdmin(Integer Admin) throws AdminException;
	Optional<Admin> findByEmail(String Email);
}
