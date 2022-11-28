package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.UserType;

@Repository
public class UserTypeDao extends AbstractJpaDao {
	
	public Optional<UserType> getByCode(final String userTypeCode) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT id, user_type_code, user_type_name, is_active, versions ")
				.append("FROM tb_user_type ")
				.append("WHERE user_type_code = :userTypeCode and is_active = true;");
		UserType row = null;
		try {
			final Object obj = createNativeQuery(query.toString()).setParameter("userTypeCode", userTypeCode).getSingleResult();
			if (obj != null) {
				final Object[] objArr = (Object[]) obj;
				row = new UserType();
				row.setId(objArr[0].toString());
				row.setUserTypeCode(objArr[1].toString());
				row.setUserTypeName(objArr[2].toString());
				row.setIsActive(Boolean.valueOf(objArr[3].toString()));
				row.setVersion(Integer.valueOf(objArr[4].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<UserType> optional = Optional.ofNullable(row);
		return optional;
	}
	
}
