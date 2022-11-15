package com.lawencon.community.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = -7005104266774312403L;
	@Column(name = "date_start", nullable = false)
	private LocalDate dateStart;
	@Column(name = "date_end", nullable = false)
	private LocalDate dateEnd;
	@Column(name = "time_start", nullable = false)
	private LocalTime timeStart;
	@Column(name = "time_end", nullable = false)
	private LocalTime timeEnd;
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
