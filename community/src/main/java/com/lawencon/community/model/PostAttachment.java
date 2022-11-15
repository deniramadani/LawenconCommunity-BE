package com.lawencon.community.model;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.lawencon.base.BaseEntity;

public class PostAttachment extends BaseEntity
	{
	private static final long serialVersionUID = -1988842476683830244L;

	@OneToOne
	@JoinColumn(name="post_id", nullable = false)
	private Post post;
	
	
	@OneToOne
	@JoinColumn(name="file_id", nullable = false)
	private File file;
}
