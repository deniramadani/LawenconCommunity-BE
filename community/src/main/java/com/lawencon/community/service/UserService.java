package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.UserTypeDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserType;
import com.lawencon.config.ApiConfiguration;

@Service
public class UserService extends BaseCoreService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ApiConfiguration apiConfiguration;
	@Autowired
	private UserTypeDao userTypeDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userDao.getByEmail(username);
		if (optional.isPresent()) {
			return new org.springframework.security.core.userdetails.User(username, optional.get().getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("Wrong Email or Password");
	}

	public Optional<User> getByEmail(final String email) {
		return userDao.getByEmail(email);
	}

	public ResponseDto insert(final User data) {
		
		final ResponseDto responseDto = new ResponseDto();
		valInsert(data);
		try {
			begin();
			
			final String password = apiConfiguration.passwordEncoder().encode(data.getPassword());
			data.setPassword(password);
			final Role role = roleDao.getByIdAndDetach(Role.class, data.getRole().getId());
			data.setRole(role);
			userDao.saveNoLogin(data, ()->"4ba262b9-258b-4ae3-b879-ee286c1db783");
			commit();
			responseDto.setMessage("Register Success");
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			responseDto.setMessage("Register Failed");
		}
		return responseDto;
	}
	
	private void valInsert(User data){
		valNotNull(data);
		valIdNull(data);
		valBkNotNull(data);
		valBkNotDuplicate(data);
		valFkFound(data);
	}

	private void valIdNotNull(final User data) {
		if (data.getId() == null) {
			throw new RuntimeException("Primary Key Id is required!");
		}
	}

	private void valIdExist(final User data) {
		final User user = userDao.getByIdAndDetach(User.class, data.getId());
		final Optional<User> optional = Optional.ofNullable(user);
		if (optional.isEmpty()) {
			throw new RuntimeException("Primay Key Id Is Exist!");
		}
	}

	private void valBkNotNull(final User data) {
		if (data.getEmail() == null) {
			throw new RuntimeException("BK Email is required!");
		}
	}

	private void valBkNotChange(final User data) {
		final User user = userDao.getByIdAndDetach(User.class, data.getId());
		final Optional<User> optional = Optional.ofNullable(user);
		if (optional.isPresent()) {
			if (optional.get().getEmail().equalsIgnoreCase(data.getEmail())) {
				throw new RuntimeException("BK Email cannot be changed!");
			}
		}
	}

	private void valFkFound(final User data) {
		final UserType userType = userTypeDao.getByIdAndDetach(UserType.class, data.getUserType().getId());
		final Optional<UserType> userTypeOptional = Optional.ofNullable(userType);
		final Role role = roleDao.getByIdAndDetach(Role.class, data.getRole().getId());
		final Optional<Role> roleOptional = Optional.ofNullable(role);

		if (!userTypeOptional.isPresent()) {
			throw new RuntimeException("User Type Not Found.");
		}
		if (!roleOptional.isPresent()) {
			throw new RuntimeException("Role Not Found.");
		}
	}

	private void valIdNull(final User data) {
		if (data.getId() != null) { 
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
	}

	private void valBkNotDuplicate(final User data) {
		final Optional<User> userOptional = userDao.getByEmail(data.getEmail());
		if (userOptional.isPresent()) {
			throw new RuntimeException("Email Already Exist.");
		}
	}

	private void valNotNull(final User data) {

		if (data.getEmail() == null) {
			throw new RuntimeException("Email Required.");
		}
		if (data.getPassword() == null) {
			throw new RuntimeException("Password Required.");
		}
		if (data.getRole().getId() == null) {
			throw new RuntimeException("Role Required.");
		}
		if (data.getUserType().getId() == null) {
			throw new RuntimeException("User Type Required.");
		}
	}

}