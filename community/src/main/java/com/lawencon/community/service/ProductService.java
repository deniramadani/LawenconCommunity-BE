package com.lawencon.community.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ProductTypeConst;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProductDao;
import com.lawencon.community.dao.ProductTypeDao;
import com.lawencon.community.dao.ScheduleDao;
import com.lawencon.community.dao.UserDao;
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
	@Autowired
	private ProductTypeDao productTypeDao;
	@Autowired
	private UserDao userDao;
	
	private void valUpdate(final Schedule data) {
		valIdNotNull(data);
		valIdExist(data);
		valNotBK(data);
	}
	
	private void valIdNotNull(final Schedule data) {
		if (data.getId() == null) {
			throw new RuntimeException("Primary Key Id is required!");
		}
		if (data.getProduct() == null) {
			throw new RuntimeException("Product Object is required!");
		}
		if (data.getProduct().getId() == null) {
			throw new RuntimeException("Primary Key Id Product is required!");
		}
	}
	
	private void valIdExist(final Schedule data) {
		final Schedule schedule = scheduleDao.getByIdAndDetach(Schedule.class, data.getId());
		final Optional<Schedule> optional = Optional.ofNullable(schedule);
		if (optional.isEmpty()) {
			throw new RuntimeException("Primay Key Id Is Not Exist!");
		}
	}
	
	private void valNotBK(final Schedule data) {
		final User owner = userDao.getByIdAndDetach(User.class, data.getProduct().getOwnerId().getId());
		final Optional<User> ownerOptional = Optional.ofNullable(owner);
		if (!ownerOptional.isPresent()) {
			throw new RuntimeException("Owner Not Found.");
		}
		final ProductType productType = productTypeDao.getByIdAndDetach(ProductType.class, data.getProduct().getProductType().getId());
		final Optional<ProductType> productTypeOptional = Optional.ofNullable(productType);
		if (!productTypeOptional.isPresent()) {
			throw new RuntimeException("Product Type Not Found.");
		}
		if(data.getDateTimeStart() == null) {
			throw new RuntimeException("Date Time Start Required!");
		}
		if(data.getDateTimeEnd() == null) {
			throw new RuntimeException("Date Time End Required!");
		}
		if(data.getProduct() == null) {
			throw new RuntimeException("Product Object is Required!");
		}
		if(data.getProduct().getTitle() == null) {
			throw new RuntimeException("Product Title is Required!");
		}
		if(data.getProduct().getContent() == null) {
			throw new RuntimeException("Product Content is Required!");
		}
		if(data.getProduct().getProvider() == null) {
			throw new RuntimeException("Product Provider is Required!");
		}
		if(data.getProduct().getLocation() == null) {
			throw new RuntimeException("Product Location is Required!");
		}
		if(data.getProduct().getPrice() == null) {
			throw new RuntimeException("Product Price is Required!");
		}
	}
	
	private void valInsert(final Schedule data) {
		valNotNull(data);
		valIdNull(data);
		valFkFound(data);
	}
	
	private void valFkFound (final Schedule data) {
		final ProductType productType = productTypeDao.getByIdAndDetach(ProductType.class, data.getProduct().getProductType().getId());
		final Optional<ProductType> productTypeOptional = Optional.ofNullable(productType);
		if (!productTypeOptional.isPresent()) {
			throw new RuntimeException("Product Type Not Found.");
		}
	}
	
	private void valIdNull(final Schedule data) {
		if(data.getId() != null) { 
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
		if(data.getProduct().getId() != null) {
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
	}
	
	private void valNotNull(final Schedule data) {
		if(data.getDateTimeStart() == null) {
			throw new RuntimeException("Date Time Start Required!");
		}
		if(data.getDateTimeEnd() == null) {
			throw new RuntimeException("Date Time End Required!");
		}
		if(data.getProduct() == null) {
			throw new RuntimeException("Product Object is Required!");
		}
		if(data.getProduct().getTitle() == null) {
			throw new RuntimeException("Product Title is Required!");
		}
		if(data.getProduct().getContent() == null) {
			throw new RuntimeException("Product Content is Required!");
		}
		if(data.getProduct().getProvider() == null) {
			throw new RuntimeException("Product Provider is Required!");
		}
		if(data.getProduct().getLocation() == null) {
			throw new RuntimeException("Product Location is Required!");
		}
		if(data.getProduct().getPrice() == null) {
			throw new RuntimeException("Product Price is Required!");
		}
		if(data.getProduct().getPhoto() == null) {
			throw new RuntimeException("File Object is Required!");
		}
		if(data.getProduct().getPhoto().getFileEncode() == null) {
			throw new RuntimeException("File Encode is Required!");
		}
		if(data.getProduct().getPhoto().getFileExtensions() == null) {
			throw new RuntimeException("File Extentions is Required!");
		}
	}
	
	public ResponseDto insert(final Schedule data)  {
		final ResponseDto response = new ResponseDto();
		Schedule insertOne = new Schedule();
		valInsert(data);
		try {
			begin();
			insertOne.setDateTimeStart(data.getDateTimeStart());
			insertOne.setDateTimeEnd(data.getDateTimeEnd());
			Product product = new Product();
			product.setTitle(data.getProduct().getTitle());
			product.setContent(data.getProduct().getContent());
			product.setProvider(data.getProduct().getProvider());
			product.setLocation(data.getProduct().getLocation());
			if(data.getProduct().getPrice().compareTo(BigDecimal.ZERO) < 0) {
				throw new RuntimeException("Price cannot be minus!");
			}
			product.setPrice(data.getProduct().getPrice());
			final User owner = new User();
			owner.setId(principalService.getAuthPrincipal());
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
			response.setMessage(e.getMessage());
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
			valUpdate(data);
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
				response.setMessage(e.getMessage());
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
				response.setMessage(e.getMessage());
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
	public Product getSubscribeId() {
		final Optional<Product> product = productDao.getSubscribeId(ProductTypeConst.SUBSCRIBE.getProductTypeCodeEnum());
		final Product result = productDao.getByIdAndDetach(Product.class, product.get().getId());
		final Optional<Product> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Product response = optional.get();
			return response;			
		} else {
			throw new RuntimeException("Product not found!");	
		}
	}
	
}
