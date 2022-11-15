package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_social_media")
@Data
@EqualsAndHashCode(callSuper = false)
public class SocialMedia extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4913307137384093935L;
	@Column(name = "social_media_name", length = 30, nullable = false)
	private String socialMediaName;
	
	@OneToOne
	@JoinColumn(name = "logo_id", nullable = false)
	private File logo;
}
