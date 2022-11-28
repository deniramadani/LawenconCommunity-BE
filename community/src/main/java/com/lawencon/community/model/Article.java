	package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_article")
@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseEntity{
	
	private static final long serialVersionUID = -911668334984227385L;

	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="content", nullable=false)
	private String content;
	
	@OneToOne
	@JoinColumn(name="photo_id", nullable = false)
	private File file;
	
	@Transient
	private User user;
	
}
