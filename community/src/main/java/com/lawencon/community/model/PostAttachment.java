package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_post_attachment")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostAttachment extends BaseEntity{
	private static final long serialVersionUID = -8180068832637928067L;

	@OneToOne
	@JoinColumn(name="post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@JoinColumn(name="file_id", nullable = false)
	private File file;
}
