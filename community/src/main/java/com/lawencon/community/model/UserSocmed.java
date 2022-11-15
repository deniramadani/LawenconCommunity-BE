package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_user_socmed")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserSocmed extends BaseEntity{
	
	private static final long serialVersionUID = 7313126493961043645L;
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	@OneToOne
	@JoinColumn(name="socmed_id", nullable=false)
	private SocialMedia socialMedia;
}
