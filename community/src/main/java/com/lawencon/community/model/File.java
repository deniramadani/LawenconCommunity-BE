package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_file")
@Data
@EqualsAndHashCode(callSuper=false)
public class File extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3108815639419706345L;
	@Column(name = "file_encode", nullable=false)
	private String fileEncode;
	@Column(name = "file_extensions", nullable=false, length=5)
	private String fileExtensions;
}
