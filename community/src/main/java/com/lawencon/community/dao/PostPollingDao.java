package com.lawencon.community.dao;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostPolling;

@Repository
public class PostPollingDao extends AbstractJpaDao {

	public PostPolling getByPost(String id) {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tpo.id, tpo.post_id, tpo.question, tpo.created_by, tpo.created_at, tpo.updated_by, tpo.updated_at, tpo.versions, tpo.is_active")
				.append(" FROM tb_post_polling AS tpo ").append("WHERE tpo.post_id = :id AND tpo.is_active = true");
		System.out.println(query.toString());
		final PostPolling postPolling = new PostPolling();
		try {
			final Object obj = createNativeQuery(query.toString()).setParameter("id", id).getSingleResult();
		if (obj != null) {
			final Object[] objArr = (Object[]) obj;
			postPolling.setId(objArr[0].toString());
			final Post post = new Post();
			post.setId(objArr[1].toString());
			postPolling.setPost(post);
			postPolling.setQuestion(objArr[2].toString());
			postPolling.setCreatedBy(objArr[3].toString());
			postPolling.setCreatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
			if (objArr[5] != null) {
				postPolling.setUpdatedBy(objArr[7].toString());
			}

			if (objArr[6] != null) {
				postPolling.setUpdatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
			}

			postPolling.setVersion(Integer.valueOf(objArr[7].toString()));
			postPolling.setIsActive(Boolean.valueOf(objArr[8].toString()));
		}}catch(Exception e) {
			e.printStackTrace();
		}
		return postPolling;
	}

}
