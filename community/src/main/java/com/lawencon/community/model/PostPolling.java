package com.lawencon.community.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_post_polling")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostPolling extends BaseEntity {

	private static final long serialVersionUID = -1020797142333453037L;
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	@Column(name = "question", length = 150, nullable = false)
	private String question;

	@Transient
	private List<PostPollingOption> postPollingOptions;

}
