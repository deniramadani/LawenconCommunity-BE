package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;

@Service
public class PositionService extends BaseCoreService{
	@Autowired
	private PositionDao positionDao;
	public List<Position> getAll(){
		return positionDao.getAll(Position.class);
	}
}
