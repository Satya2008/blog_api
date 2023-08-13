package com.masai.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entities.Admin;
import com.masai.entities.MyUser;
import com.masai.exceptions.AdminException;
import com.masai.exceptions.UserException;
import com.masai.repositories.AdminRepository;
import com.masai.repositories.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin createAdmin(Admin admin) throws AdminException {
		return adminRepository.save(admin);
	}

	@Override
	public List<MyUser> getAllUsers() {
		List<MyUser> myUsers = userRepository.findAll();
		return myUsers;

	}

	@Override
	public Admin updateAdmin(Admin admin, Integer adminId) throws AdminException {
		 Admin us  = adminRepository.findById(adminId)
		    		.orElseThrow(()-> new AdminException("Admin not found by given adminId" + adminId));
		    
		    us.setName(admin.getName());
		    us.setEmail(admin.getEmail());
		    
			return adminRepository.save(us);
	}

	@Override
	public Admin getAdminById(Integer AdminId) throws AdminException {
		Optional<Admin> admin = adminRepository.findById(AdminId);
		if(!admin.isPresent()) throw new UserException("Admin not found by given id "+ AdminId);
		Admin us = admin.get();
		return us;
	}

	@Override
	public Admin deleteAdmin(Integer adminId) throws AdminException {
		  Optional<Admin> optionalUser = adminRepository.findById(adminId);
		    if (optionalUser.isPresent()) {
		        Admin admin = optionalUser.get();
		        adminRepository.delete(admin);
		        return admin;
		    } else {
		        throw new UserException("Admin not found for the given id: " + adminId);
		    }
	}

	@Override
	public Optional<Admin> findByEmail(String Email) {
		Optional<Admin> user= adminRepository.findByEmail(Email);
		 if(user.isEmpty()) throw new AdminException("No admin found");
		 return user;
	}

}
