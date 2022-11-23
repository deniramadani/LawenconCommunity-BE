package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_user_socmed")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserSocmed extends BaseEntity{

	private static final long serialVersionUID = 8681486594160677008L;

	@Column(name="facebook", nullable=false)
	private String facebook;

	@Column(name="instagram", nullable=false)
	private String instagram;
	
	@Column(name="linkedin", nullable=false)
	private String linkedin;
	
}
