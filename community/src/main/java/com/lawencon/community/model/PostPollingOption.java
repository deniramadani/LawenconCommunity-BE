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
@Table(name = "tb_post_polling_option")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostPollingOption extends BaseEntity {
	
	private static final long serialVersionUID = 6125557107335804675L;
	
	@OneToOne
	@JoinColumn(name="post_polling_id", nullable=false)
	private PostPolling postPolling;
	
	@Column(name="content", length=150, nullable=false)
	private String content;
	
	@Transient
	private Integer totalResponse;

}
