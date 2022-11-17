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
@Table(name = "tb_payment")
@Data
@EqualsAndHashCode(callSuper = false)
public class Payment extends BaseEntity {
	private static final long serialVersionUID = -1208154327364977723L;
	@Column(name = "transaction_code", nullable = false)
	private String transactionCode;
	@Column(name = "approval", nullable = false)
	private Boolean approval;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@OneToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	@OneToOne
	@JoinColumn(name = "transfer_photo_id", nullable = false)
	private File file;

}
