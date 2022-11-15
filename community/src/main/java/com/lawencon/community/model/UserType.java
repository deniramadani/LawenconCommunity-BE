package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_user_type", uniqueConstraints = { 
		@UniqueConstraint(
				name = "user_type_bk", 
				columnNames = { "user_type_code" }
		) 
})
@Data
@EqualsAndHashCode(callSuper=false)
public class UserType extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7060155208384961159L;
	@Column(name = "user_type_code", nullable=false, length=6)
	private String userTypeCode;
	@Column(name = "user_type_name", nullable=false, length=30)
	private String userTypeName;
}
