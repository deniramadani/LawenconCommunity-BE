package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.UserTypeDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.UserType;

@Service
public class UserTypeService extends BaseCoreService{
	@Autowired
	private UserTypeDao userTypeDao;
	public List<UserType> getAll(){
		return userTypeDao.getAll(UserType.class);
	}
}
