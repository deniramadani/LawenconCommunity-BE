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
@Table(name = "tb_post")
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity {
	private static final long serialVersionUID = -3991998266586527586L;
	@Column(length = 100, nullable = false)
	private String title;
	@Column(nullable = false)
	private String body;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne
	@JoinColumn(name = "type_post_id", nullable = false)
	private PostType postType;

	@Transient
	private List<PostAttachment> file;

	@Transient
	private String question;

	@Transient
	private Integer totalLike;

	@Transient
	private String like_id;

	@Transient
	private String bookmark_id;

	@Transient
	private Integer totalComment;
	
	@Transient
	private List<PostPollingOption> postPollingOption;
	
	@Transient
	private List<File> pfile;
	
	@Transient
	private List<Comment> comments;
	
}
