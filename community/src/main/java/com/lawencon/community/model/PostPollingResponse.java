package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_user_polling_response")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostPollingResponse extends BaseEntity {
	
	private static final long serialVersionUID = -7402706420937452885L;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToOne
	@JoinColumn(name="polling_option_id",nullable=false)
	private PostPollingOption postPollingOption;

}
