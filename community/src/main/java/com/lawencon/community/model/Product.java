package com.lawencon.community.model;

import com.lawencon.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name="products")
@Getter @Setter
public class Product extends BaseEntity{
/**
	 * 
	 */
	private static final long serialVersionUID = -6027604015861212694L;
//	@Column(name = "product_code", nullable=false, length=10, unique=true)
	private String productCode;
//	@Column(name = "product_name", nullable=false, length=100)
	private String productName;
	
}
