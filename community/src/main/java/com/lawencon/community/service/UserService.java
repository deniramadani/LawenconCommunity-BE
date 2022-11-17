package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.constant.RoleConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.UserTypeDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
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
	@Autowired
	private IndustryDao industryDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private FileDao fileDao;

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
	
	public ResponseDto insertSuperAdmin(final User data) {
		return insert(data,RoleConst.SUPERADMIN.getRoleCodeEnum());
	}
	
	public ResponseDto insertAdmin(final User data) {
		return insert(data,RoleConst.ADMIN.getRoleCodeEnum());
	}
	
	public ResponseDto insertMember(final User data) {
		return insert(data,RoleConst.MEMBER.getRoleCodeEnum());
	}

	private ResponseDto insert(final User data, final String code) {
		final ResponseDto responseDto = new ResponseDto();
		final Optional<Role> roleCode = roleDao.getByCode(code);
		if(roleCode.isEmpty()) {
			throw new RuntimeException("Role Constant not found!");	
		}
		Optional<UserType> userTypeCode = null;
		if (code.equalsIgnoreCase(RoleConst.MEMBER.getRoleCodeEnum())) {
			userTypeCode = userTypeDao.getByCode(UserTypeConst.BASIC.getUserTypeCodeEnum());			
		} else {
			userTypeCode = userTypeDao.getByCode(UserTypeConst.PREMIUM.getUserTypeCodeEnum());	
		}
		if(userTypeCode.isEmpty()) {
			throw new RuntimeException("User Type Constant found!");	
		}
		data.setRole(roleCode.get());
		data.setUserType(userTypeCode.get());
		valInsert(data);
		try {
			begin();
			final String password = apiConfiguration.passwordEncoder().encode(data.getPassword());
			data.setPassword(password);
			final Role role = roleDao.getByIdAndDetach(Role.class, roleCode.get().getId());
			data.setRole(role);
			final UserType userType = userTypeDao.getByIdAndDetach(UserType.class, userTypeCode.get().getId());
			data.setUserType(userType);
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
	
	private void valInsert(final User data){
		valNotNull(data);
		valIdNull(data);
		valBkNotNull(data);
		valBkNotDuplicate(data);
		valFkFound(data);
	}
	
	public ResponseDto update(final User data) {
		final ResponseDto responseDto = new ResponseDto();
		valUpdate(data);
		final User result = userDao.getByIdAndDetach(User.class, data.getId());
		final Optional<User> optional = Optional.ofNullable(result);
		try {
			if(optional.isEmpty()) {
				throw new RuntimeException("User not found!");				
			} 
			begin();
			if(data.getFullname() != null) {
				result.setFullname(data.getFullname());
			}
			if(data.getPassword() != null) {
				final String password = apiConfiguration.passwordEncoder().encode(data.getPassword());
				result.setPassword(password);									
			}
			if(data.getCompany() != null) {
				result.setCompany(data.getCompany());
			}
			if(data.getIndustry() != null) {
				result.setIndustry(data.getIndustry());
			}
			if(data.getPosition() != null) {
				result.setPosition(data.getPosition());
			}
			if(data.getPhoto() != null) {
				final File file = fileDao.saveAndFlush(data.getPhoto());
				result.setPhoto(file);
			}
			if(data.getPhoneNumber() != null) {
				result.setPhoneNumber(data.getPhoneNumber());
			}
			if(data.getAddress() != null) {
				result.setAddress(data.getAddress());
			}
			if(data.getDateOfBirth() != null) {
				result.setDateOfBirth(data.getDateOfBirth());
			}
			userDao.saveAndFlush(result);
			commit();
			responseDto.setMessage(ResponseConst.UPDATED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			responseDto.setMessage(ResponseConst.FAILED.getResponse());
		}
		return responseDto;
	}
	
	private void valUpdate(final User data) {
		valIdNotNull(data);
		valIdExist(data);
		valBkNotNull(data);
		valBkNotChange(data);
		valNotBK(data);
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
			throw new RuntimeException("Primay Key Id Is Not Exist!");
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
			if (!optional.get().getEmail().equals(data.getEmail())) {
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
		if (data.getFullname() == null) {
			throw new RuntimeException("Fullname is Required.");
		}
	}
	
	private void valNotBK(final User data) {
		final Role role = roleDao.getByIdAndDetach(Role.class, data.getRole().getId());
		final Optional<Role> roleOptional = Optional.ofNullable(role);
		if (!roleOptional.isPresent()) {
			throw new RuntimeException("Role Not Found.");
		}
		final UserType userType = userTypeDao.getByIdAndDetach(UserType.class, data.getUserType().getId());
		final Optional<UserType> userTypeOptional = Optional.ofNullable(userType);
		if (!userTypeOptional.isPresent()) {
			throw new RuntimeException("User Type Not Found.");
		}
		if(data.getIndustry() != null) {
			final Industry industry = industryDao.getByIdAndDetach(Industry.class, data.getIndustry().getId());
			final Optional<Industry> industryTypeOptional = Optional.ofNullable(industry);
			if (!industryTypeOptional.isPresent()) {
				throw new RuntimeException("Industry Not Found.");
			}
		}
		if(data.getPosition() != null) {
			final Position position = positionDao.getByIdAndDetach(Position.class, data.getPosition().getId());
			final Optional<Position> positionOptional = Optional.ofNullable(position);
			if (!positionOptional.isPresent()) {
				throw new RuntimeException("Position Not Found.");
			}
		}
	}
	
}