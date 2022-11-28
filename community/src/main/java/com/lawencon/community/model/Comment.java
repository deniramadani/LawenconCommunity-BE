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
@Table(name = "tb_comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity{
	
	private static final long serialVersionUID = -6951535414837311039L;

	@Column(name="content", nullable = false)
	private String content;

	@OneToOne
	@JoinColumn(name="post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
}
