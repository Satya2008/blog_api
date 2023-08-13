package com.masai.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.masai.entities.Admin;
import com.masai.entities.MyUser;

public class ManualUserDetails implements UserDetails {

	private MyUser myUser;
	
	
	
	public ManualUserDetails(MyUser myUser) {
		super();
		this.myUser=myUser;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList();
	}

	@Override
	public String getPassword() {
		return myUser.getPassword();
	}

	@Override
	public String getUsername() {
		return myUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
