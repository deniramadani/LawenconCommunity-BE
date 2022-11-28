package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.constant.ProductTypeConst;
import com.lawencon.community.dao.ProductTypeDao;
import com.lawencon.community.model.ProductType;

@Service
public class ProductTypeService {
	
	@Autowired
	private ProductTypeDao productTypeDao;
	
	public List<ProductType> getAllEC() {
		return productTypeDao.getAllEC(ProductTypeConst.SUBSCRIBE.getProductTypeCodeEnum());
	}
	
}