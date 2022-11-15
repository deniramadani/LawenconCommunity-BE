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
@Table(name = "tb_verification_code")
@Data
@EqualsAndHashCode(callSuper = false)
public class VerificationCode extends BaseEntity{
	
	private static final long serialVersionUID = 8709838190352653564L;
	@Column(name="verification_code", length=6, nullable=false)
	private String verificationCode;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
}
