package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostAttachment;

@Repository
public class PostAttachmentDao extends AbstractJpaDao {
	public List<PostAttachment> getAllByPost(String id) {
		final StringBuilder query = new StringBuilder().append("SELECT * ")
				.append("FROM tb_post_attachment AS tpa ")
				.append("WHERE tpa.post_id = :id AND tpa.is_active = true");
		System.out.println(query.toString());
		@SuppressWarnings("unchecked")
		final List<PostAttachment> files = ConnHandler.getManager().createNativeQuery(query.toString(), PostAttachment.class)
				.setParameter("id", id).getResultList();
		return files;
	}
}
