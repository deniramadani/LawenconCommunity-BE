package com.lawencon.community.model;

import com.lawencon.base.BaseEntity;

//@Entity
//@Table(name="products")
public class Product extends BaseEntity{
	
//	@Column(name = "product_code", nullable=false, length=10, unique=true)
	private String productCode;
//	@Column(name = "product_name", nullable=false, length=100)
	private String productName;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(final String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(final String productName) {
		this.productName = productName;
	}
}
