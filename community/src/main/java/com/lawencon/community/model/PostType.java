package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_post_type")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostType extends BaseEntity {
	private static final long serialVersionUID = 4580850498952494210L;
	@Column(name="post_type_code", length =5, nullable=false)
	private String postTypeCode;
	@Column(name="post_type_name", length =100, nullable=false)
	private String postTypeName;
	
}
