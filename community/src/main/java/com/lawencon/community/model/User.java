package com.lawencon.community.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_user", uniqueConstraints = { 
		@UniqueConstraint(
				name = "user_bk", 
				columnNames = { "email" }
		) 
})
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -168649940255594139L;
	@Column(name = "fullname", nullable=false, length=35)
	private String fullname;
	@Column(name = "email", nullable=false, length=30)
	private String email;
	@Column(name = "password", nullable=false)
	private String password;
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	@Column(name = "address")
	private String address;
	@Column(name = "phone_number", length=15)
	private String phoneNumber;
	@Column(name = "company", length=100)
	private String company;
	@Column(name = "ballance")
	private BigDecimal ballance;
	
	@OneToOne
	@JoinColumn(name = "role_id", nullable=false)
	private Role role;
	@OneToOne
	@JoinColumn(name = "industry_id")
	private Industry industry;
	@OneToOne
	@JoinColumn(name = "position_id")
	private Position position;
	@OneToOne
	@JoinColumn(name = "photo_id")
	private File photo;
	@OneToOne
	@JoinColumn(name = "user_type_id", nullable=false)
	private UserType userType;
	
	@PrePersist
	public void preInsert() {
		this.ballance = BigDecimal.ZERO;
	}
}
