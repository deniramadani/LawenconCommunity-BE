package com.lawencon.community.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_product")
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {

	private static final long serialVersionUID = 5464381464520591039L;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private String provider;
	@Column(nullable = false)
	private String location;
	@Column(nullable = false)
	private BigDecimal price;
	@OneToOne
	@JoinColumn(name = "type_product_id", nullable = false)
	private ProductType productType;
	@OneToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User ownerId;

}
