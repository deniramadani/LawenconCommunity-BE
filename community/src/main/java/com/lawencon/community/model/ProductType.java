package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_product_type", uniqueConstraints = { 
		@UniqueConstraint(
				name = "product_type_bk", 
				columnNames = { "product_type_code" }
		) 
})
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductType extends BaseEntity{
	private static final long serialVersionUID = 4692780059942588299L;
	@Column(name = "product_type_code", nullable=false, length=6)
	private String productTypeCode;
	@Column(name = "product_type_name", nullable=false)
	private String productTypeName;
	
}
