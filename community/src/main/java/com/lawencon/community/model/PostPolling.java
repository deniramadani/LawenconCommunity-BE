package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.lawencon.base.BaseEntity;

public class PostPolling extends BaseEntity {
	private static final long serialVersionUID = -1020797142333453037L;
	@OneToOne
	@JoinColumn(name="post_id", nullable=false)
	private Post post;
	@Column(name="question", length=150, nullable=false)
	private String question;

}
