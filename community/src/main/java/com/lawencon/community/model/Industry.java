package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_industry")
@Data
@EqualsAndHashCode(callSuper=false)
public class Industry extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2234539192122952482L;
	@Column(name = "industry_name", nullable=false, length=30)
	private String industryName;
}
