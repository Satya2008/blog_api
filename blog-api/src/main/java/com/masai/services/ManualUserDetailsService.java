package com.masai.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.entities.Admin;
import com.masai.entities.MyUser;
import com.masai.repositories.AdminRepository;
import com.masai.repositories.UserRepository;


@Service
public class ManualUserDetailsService implements UserDetailsService {
    
	@Autowired
	 private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	public boolean isAdmin(String email) {
	   Optional<Admin> admin = adminRepository.findByEmail(email);
		if(admin.isPresent()) return true;
		else return false;
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		if(isAdmin(email)) {
			Optional<Admin> admin = adminRepository.findByEmail(email);
			 
			 if(admin.isEmpty()) throw new UsernameNotFoundException("Admin not found");
			 Admin us = admin.get();
			 
//			when we want to check manually
//			ManualUserDetails manualUserDetails = new ManualUserDetails(us);
//			return manualUserDetails;
			
			 
			List<GrantedAuthority> authorities = new ArrayList<>() ;
			SimpleGrantedAuthority autho = new SimpleGrantedAuthority("ROLE_"+us.getRole().toUpperCase()) ;
			authorities.add(autho) ;
			User secUser = new User(us.getEmail(), us.getPassword(),  authorities) ;
			return secUser ;
		}else {
			
			Optional<MyUser> myUser = userRepository.findByEmail(email);
				 
				 if(myUser.isEmpty()) throw new UsernameNotFoundException("User not found");
				 MyUser us = myUser.get();
				 
//				when we want to check manually
//				ManualUserDetails manualUserDetails = new ManualUserDetails(us);
//				return manualUserDetails;
				
				 
				List<GrantedAuthority> authorities = new ArrayList<>() ;
				SimpleGrantedAuthority autho = new SimpleGrantedAuthority("ROLE_"+us.getRole().toUpperCase()) ;
				authorities.add(autho) ;
				User secUser = new User(us.getEmail(), us.getPassword(),  authorities) ;
				return secUser ;

				
			}
		}


}
