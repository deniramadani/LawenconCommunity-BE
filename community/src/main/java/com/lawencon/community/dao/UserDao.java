package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;

@Repository
public class UserDao extends AbstractJpaDao {

	public Optional<User> getByEmail(final String email) {
		final String sql = "SELECT tb_user.id AS id, email, password, fullname, role_code, role_name, "
				+ "tb_user.is_actives, tb_user.versions "
				+ "FROM tb_user "
				+ "INNER JOIN tb_role ON tb_user.role_id = tb_role.id "
				+ "WHERE email = :email AND tb_user.is_actives = true";
		User row = null;
		try {
			final Object userObj = ConnHandler.getManager().createNativeQuery(sql).setParameter("email", email).getSingleResult();
			if (userObj != null) {
				Object[] objArr = (Object[]) userObj;
				row = new User();
				final Role role = new Role();
				row.setId(objArr[0].toString());
				row.setEmail(objArr[1].toString());
				row.setPassword(objArr[2].toString());
				row.setFullname(objArr[3].toString());
				role.setRoleCode(objArr[4].toString());
				role.setRoleName(objArr[5].toString());
				row.setIsActive(Boolean.valueOf(objArr[6].toString()));
				row.setVersion(Integer.valueOf(objArr[7].toString()));
				row.setRole(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<User> optional = Optional.ofNullable(row);
		return optional;
	}

}
