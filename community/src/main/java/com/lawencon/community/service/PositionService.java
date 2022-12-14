package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Position;

@Service
public class PositionService extends BaseCoreService {
	
	@Autowired
	private PositionDao positionDao;
	
	private void valInsert(final Position data){
		valNotNull(data);
		valIdNull(data);
	}
	
	private void valNotNull(final Position data) {
		if (data.getPositionName() == null) {
			throw new RuntimeException("Position Name is Required!");
		}
	}

	private void valIdNull(final Position data) {
		if (data.getId() != null) { 
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
	}
	
	private void valUpdate(final Position data) {
		valIdNotNull(data);
		valIdExist(data);
		valNotBK(data);
	}
	
	private void valIdNotNull(final Position data) {
		if (data.getId() == null) {
			throw new RuntimeException("Primary Key Id is required!");
		}
	}

	private void valIdExist(final Position data) {
		final Position result = positionDao.getByIdAndDetach(Position.class, data.getId());
		final Optional<Position> optional = Optional.ofNullable(result);
		if (optional.isEmpty()) {
			throw new RuntimeException("Primay Key Id Is Not Exist!");
		}
	}
	
	private void valNotBK(final Position data) {
		if (data.getPositionName() == null) {
			throw new RuntimeException("Position Name is Required!");
		}
	}
	
	public ResponseDto insert(final Position data)  {
		final ResponseDto response = new ResponseDto();
		valInsert(data);
		try {
			begin();
			final Position insertOne = new Position();
			insertOne.setPositionName(data.getPositionName());
			positionDao.save(insertOne);			
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
	
	public ResponseDto update(final Position data)  {
		final ResponseDto response = new ResponseDto();
		final Position result = positionDao.getByIdAndDetach(Position.class, data.getId());
		final Optional<Position> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Position updateOne = optional.get();
			valUpdate(data);
			try {
				begin();
				updateOne.setPositionName(data.getPositionName());
				updateOne.setIsActive(data.getIsActive());
				updateOne = positionDao.saveAndFlush(updateOne);		
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
	
	public List<Position> getAll(final Integer start, final Integer limit){
		return positionDao.getAll(Position.class, start, limit);
	}
	
	public Position getById(final String id) {
		final Position result = positionDao.getByIdAndDetach(Position.class, id);
		final Optional<Position> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Position findOne = optional.get();
			return findOne;			
		} else {
			throw new RuntimeException("Position not found!");	
		}
	}
	
	public ResponseDto deleteById(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Position result = positionDao.getByIdAndDetach(Position.class, id);
		final Optional<Position> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Position deleteOne = optional.get();
			try {
				begin();
				positionDao.deleteById(Position.class, deleteOne.getId());		
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
