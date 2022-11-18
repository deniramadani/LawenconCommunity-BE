package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Industry;
@Service
public class IndustryService extends BaseCoreService {
	@Autowired
	private IndustryDao industryDao;
	
	public List<Industry> getAll(){
		return industryDao.getAll(Industry.class);
	}
}
