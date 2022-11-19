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
@Table(name = "tb_article")
@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseEntity{
	private static final long serialVersionUID = -4061829374465851177L;

	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="content", nullable=false)
	private String content;
	
	@OneToOne
	@JoinColumn(name="photo_id", nullable = false)
	private File file;
}
