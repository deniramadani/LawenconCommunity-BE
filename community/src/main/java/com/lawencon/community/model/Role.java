package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_role", uniqueConstraints = { 
		@UniqueConstraint(
				name = "role_bk", 
				columnNames = { "role_code" }
		) 
})
@Data
@EqualsAndHashCode(callSuper=false)
public class Role extends BaseEntity{
	private static final long serialVersionUID = 1019583607543236514L;
	@Column(name = "role_code", nullable=false, length=6)
	private String roleCode;
	@Column(name = "role_name", nullable=false, length=30)
	private String roleName;
}
