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
@Table(name = "tb_post")
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity{
	private static final long serialVersionUID = 5314289863412075987L;
	@Column(length=100, nullable=false)
	private String title;
	@Column(nullable=false)
	private String body;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToOne
	@JoinColumn(name="type_post_id", nullable = false)
	private PostType postType;
	
}
