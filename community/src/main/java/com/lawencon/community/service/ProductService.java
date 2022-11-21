package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ProductTypeConst;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProductDao;
import com.lawencon.community.dao.ScheduleDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Product;
import com.lawencon.community.model.ProductType;
import com.lawencon.community.model.Schedule;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ProductService extends BaseCoreService {
	
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService principalService;
	
	public ResponseDto insert(final Schedule data)  {
		final ResponseDto response = new ResponseDto();
		Schedule insertOne = new Schedule();
		try {
			begin();
			insertOne.setDateTimeStart(data.getDateTimeStart());
			insertOne.setDateTimeEnd(data.getDateTimeEnd());
			Product product = new Product();
			product.setTitle(data.getProduct().getTitle());
			product.setContent(data.getProduct().getContent());
			product.setProvider(data.getProduct().getProvider());
			product.setLocation(data.getProduct().getLocation());
			product.setPrice(data.getProduct().getPrice());
			final User owner = new User();
			owner.setId(data.getProduct().getOwnerId().getId());
			product.setOwnerId(owner);
			final ProductType productType = new ProductType();
			productType.setId(data.getProduct().getProductType().getId());
			product.setProductType(productType);
			//file is required
			File file = new File();
			file.setFileEncode(data.getProduct().getPhoto().getFileEncode());
			file.setFileExtensions(data.getProduct().getPhoto().getFileExtensions());
			file = fileDao.save(file);
			product.setPhoto(file);
			product = productDao.save(product);
			insertOne.setProduct(product);
			insertOne = scheduleDao.save(insertOne);			
			commit();
			response.setMessage(ResponseConst.CREATED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}
	
	public ResponseDto update(final Schedule data)  {
		final ResponseDto response = new ResponseDto();
		final Schedule result = scheduleDao.getByIdAndDetach(Schedule.class, data.getId());
		final Optional<Schedule> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Schedule updateOne = optional.get();
			try {
				begin();
				updateOne.setDateTimeStart(data.getDateTimeStart());
				updateOne.setDateTimeEnd(data.getDateTimeEnd());
				updateOne.setIsActive(data.getIsActive());
				final Product productId = productDao.getByIdAndDetach(Product.class, data.getProduct().getId());
				Optional<Product> productOpt = Optional.ofNullable(productId);
				if(productOpt.isPresent()) {
					Product product = productOpt.get();
					product.setTitle(data.getProduct().getTitle());
					product.setContent(data.getProduct().getContent());
					product.setProvider(data.getProduct().getProvider());
					product.setLocation(data.getProduct().getLocation());
					product.setPrice(data.getProduct().getPrice());
					if(data.getProduct().getPhoto() != null) {
						File file = new File();
						file.setFileEncode(data.getProduct().getPhoto().getFileEncode());
						file.setFileExtensions(data.getProduct().getPhoto().getFileExtensions());
						file = fileDao.save(file);
						product.setPhoto(file);					
					}
					product = productDao.saveAndFlush(product);
					updateOne.setProduct(product);
				}
				updateOne = scheduleDao.saveAndFlush(updateOne);
				commit();
				response.setMessage(ResponseConst.UPDATED.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
	public Schedule getById(final String id) {
		final Optional<Schedule> optional = scheduleDao.getByIdProduct(id);
		if(optional.isPresent()) {
			final Schedule result = optional.get();
			return result;			
		} else {
			throw new RuntimeException("Product not found!");	
		}
	}
	
	public List<Schedule> getAllSchedule(final Integer start, final Integer limit, final String code) {
		String inputCode = null;
		if(ProductTypeConst.EVENT.getProductTypeCodeEnum().equalsIgnoreCase(code)) {
			inputCode = ProductTypeConst.EVENT.getProductTypeCodeEnum();
		} else {
			inputCode = ProductTypeConst.COURSE.getProductTypeCodeEnum();
		}
		return scheduleDao.getAllSchedule(start, limit, inputCode);
	}
	
	public List<Schedule> getAllByUserId(final Integer start, final Integer limit, final String code) {
		String inputCode = null;
		if(ProductTypeConst.EVENT.getProductTypeCodeEnum().equalsIgnoreCase(code)) {
			inputCode = ProductTypeConst.EVENT.getProductTypeCodeEnum();
		} else {
			inputCode = ProductTypeConst.COURSE.getProductTypeCodeEnum();
		}
		final String userId = principalService.getAuthPrincipal();
		return scheduleDao.getAllByUserId(start, limit, userId, inputCode);
	}
	
	public ResponseDto delete(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Optional<Schedule> optional = scheduleDao.getByIdProduct(id);
		if(optional.isPresent()) {
			Schedule updateOne = optional.get();
			try {
				begin();
				updateOne.setIsActive(false);
				updateOne = scheduleDao.saveAndFlush(updateOne);
				commit();
				response.setMessage(ResponseConst.DELETED.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
}
