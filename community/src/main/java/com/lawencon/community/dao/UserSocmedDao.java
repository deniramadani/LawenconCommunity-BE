package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserSocmed;

@Repository
public class UserSocmedDao extends AbstractJpaDao{
	
	public Optional<UserSocmed> getByUserId(final String id) {
		final StringBuilder query = new StringBuilder()
			.append("SELECT id, facebook, instagram, linkedin, user_id ")
			.append("FROM tb_user_socmed tus ")
			.append("WHERE tus.user_id = :id ");
		UserSocmed row = null;
		try {
			final Object objCol = createNativeQuery(query.toString()).setParameter("id", id).getSingleResult();
			if (objCol != null) {
				Object[] objArr = (Object[]) objCol;
				row = new UserSocmed();
				row.setId(objArr[0].toString());
				row.setFacebook(objArr[1].toString());
				row.setInstagram(objArr[2].toString());
				row.setLinkedin(objArr[3].toString());
				final User user = new User();
				user.setId(objArr[4].toString());
				row.setUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<UserSocmed> optional = Optional.ofNullable(row);
		return optional;
	}
	
}
