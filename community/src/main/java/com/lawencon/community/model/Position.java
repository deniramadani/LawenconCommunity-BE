package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_position")
@Data
@EqualsAndHashCode(callSuper=false)
public class Position extends BaseEntity {
	
	private static final long serialVersionUID = -8552107889906860067L;
	
	@Column(name = "position_name", nullable=false, length=30)
	private String positionName;
	
}
