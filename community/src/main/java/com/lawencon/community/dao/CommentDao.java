package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Comment;

@Repository
public class CommentDao extends AbstractJpaDao {

	public List<Comment> getByPost(final String id) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT * ")
				.append("FROM tb_comment AS tc ")
				.append("WHERE tc.post_id = :id AND tc.is_active = true ")
				.append("ORDER BY tc.created_at DESC ");
		@SuppressWarnings("unchecked")
		final List<Comment> comments = ConnHandler.getManager().createNativeQuery(query.toString(), Comment.class)
				.setParameter("id", id).getResultList();
		return comments;
	}

}
