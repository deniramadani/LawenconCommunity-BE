package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.User;
import com.lawencon.config.ApiConfiguration;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired private UserDao userDao;
	@Autowired private ApiConfiguration apiConfiguration;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userDao.getByEmail(username);
		if(optional.isPresent()) {
			return new org.springframework.security.core
					.userdetails.User(username, optional.get().getPassword(), new ArrayList<>());
		}
		throw new UsernameNotFoundException("Wrong Email or Password");
	}
	
	public Optional<User> getByEmail(final String email) {
		return userDao.getByEmail(email);
	}
	
	public User insert(final User data) {

//	    created_by varchar(36) not null,
		User row = new User();
		try {
			ConnHandler.begin();
			final String password = apiConfiguration.passwordEncoder().encode(data.getPassword());
			data.setPassword(password);
			row = userDao.save(data);
		} catch (Exception e) {
			e.printStackTrace();
			ConnHandler.rollback();
		}
		return row;
	}
	
	private void validNotNull(final User data){
		
	}
	
	private void validExist(final User data) {
		
	}
	
	private void valBkNotNull(final User data) {
		
	}
	
	private void valBkNotChange(final User data) {
		
	}
	
	private void valNotBk(final User data) {
		
	}
	
	private void valIdNull(final User data) {
		
	}
	
	private void valBkNotDuplicate(final User data) {
		
	}
	
	private void valFkFound(final User data) {
		
	}
	
}