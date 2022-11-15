package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_post_bookmark")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostBookmark extends BaseEntity{

	private static final long serialVersionUID = -8133763943128064863L;

	@OneToOne
	@JoinColumn(name="post_id", nullable = false)
	private Post post;
	
	
	@OneToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
}
