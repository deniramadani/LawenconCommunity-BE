package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.PostType;

@Repository
public class PostTypeDao extends AbstractJpaDao{
	
	public Optional<PostType> getByCode(final String postCode) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT id, post_type_code, post_type_name, is_active, versions ")
				.append("FROM tb_post_type ")
				.append("WHERE post_type_code = :postCode AND is_active = true");
		PostType row = null;
		try {
			final Object obj = createNativeQuery(query.toString()).setParameter("postCode", postCode).getSingleResult();
			if (obj != null) {
				final Object[] objArr = (Object[]) obj;
				row = new PostType();
				row.setId(objArr[0].toString());
				row.setPostTypeCode(objArr[1].toString());
				row.setPostTypeName(objArr[2].toString());
				row.setIsActive(Boolean.valueOf(objArr[3].toString()));
				row.setVersion(Integer.valueOf(objArr[4].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<PostType> optional = Optional.ofNullable(row);
		return optional;
	}
	
}
