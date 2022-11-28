package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_schedule")
@Data
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseEntity{

	private static final long serialVersionUID = -147122213635167724L;
	
	@Column(name = "date_time_start", nullable = false)
	private LocalDateTime dateTimeStart;
	
	@Column(name = "date_time_end", nullable = false)
	private LocalDateTime dateTimeEnd;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
