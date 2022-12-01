package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_schedule")
@Data
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseEntity{

	private static final long serialVersionUID = -147122213635167724L;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	@Column(name = "date_time_start", nullable = false)
	private LocalDateTime dateTimeStart;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	@Column(name = "date_time_end", nullable = false)
	private LocalDateTime dateTimeEnd;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
