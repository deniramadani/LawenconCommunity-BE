package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Industry;
@Service
public class IndustryService extends BaseCoreService {
	
	@Autowired
	private IndustryDao industryDao;
	
	private void valInsert(final Industry data){
		valNotNull(data);
		valIdNull(data);
	}
	
	private void valNotNull(final Industry data) {
		if (data.getIndustryName() == null) {
			throw new RuntimeException("Industry Name is Required!");
		}
	}

	private void valIdNull(final Industry data) {
		if (data.getId() != null) { 
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
	}
	
	private void valUpdate(final Industry data) {
		valIdNotNull(data);
		valIdExist(data);
		valNotBK(data);
	}
	
	private void valIdNotNull(final Industry data) {
		if (data.getId() == null) {
			throw new RuntimeException("Primary Key Id is required!");
		}
	}

	private void valIdExist(final Industry data) {
		final Industry result = industryDao.getByIdAndDetach(Industry.class, data.getId());
		final Optional<Industry> optional = Optional.ofNullable(result);
		if (optional.isEmpty()) {
			throw new RuntimeException("Primay Key Id Is Not Exist!");
		}
	}
	
	private void valNotBK(final Industry data) {
		if (data.getIndustryName() == null) {
			throw new RuntimeException("Industry Name is Required!");
		}
	}
	
	public ResponseDto insert(final Industry data)  {
		final ResponseDto response = new ResponseDto();
		valInsert(data);
		try {
			begin();
			final Industry insertOne = new Industry();
			insertOne.setIndustryName(data.getIndustryName());
			industryDao.save(insertOne);			
			commit();
			response.setMessage(ResponseConst.CREATED.getResponse());
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}
	
	public ResponseDto update(final Industry data)  {
		final ResponseDto response = new ResponseDto();
		final Industry result = industryDao.getByIdAndDetach(Industry.class, data.getId());
		final Optional<Industry> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Industry updateOne = optional.get();
			valUpdate(data);
			try {
				begin();
				updateOne.setIndustryName(data.getIndustryName());
				updateOne.setIsActive(data.getIsActive());
				updateOne = industryDao.saveAndFlush(updateOne);		
				commit();
				response.setMessage(ResponseConst.UPDATED.getResponse());
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
	public List<Industry> getAll(final Integer start, final Integer limit){
		return industryDao.getAll(Industry.class, start, limit);
	}
	
	public Industry getById(final String id) {
		final Industry result = industryDao.getByIdAndDetach(Industry.class, id);
		final Optional<Industry> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Industry findOne = optional.get();
			return findOne;			
		} else {
			throw new RuntimeException("Industry not found!");	
		}
	}
	
	public ResponseDto deleteById(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Industry result = industryDao.getByIdAndDetach(Industry.class, id);
		final Optional<Industry> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Industry deleteOne = optional.get();
			try {
				begin();
				industryDao.deleteById(Industry.class, deleteOne.getId());		
				commit();
				response.setMessage(ResponseConst.DELETED.getResponse());
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
}
