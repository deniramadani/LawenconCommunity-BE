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

	@Column(name="facebook")
	private String facebook;

	@Column(name="instagram")
	private String instagram;
	
	@Column(name="linkedin")
	private String linkedin;
	
}
