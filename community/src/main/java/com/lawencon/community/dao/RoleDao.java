package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Role;
@Repository
public class RoleDao extends AbstractJpaDao{
	public Optional<Role> getByCode(final String roleCode) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT id, role_code, role_name, is_active ")
				.append("FROM tb_role ")
				.append("WHERE role_code = :roleCode AND is_active = true");
		Role row = null;
		try {
			final Object obj = createNativeQuery(query.toString()).setParameter("roleCode", roleCode).getSingleResult();
			if (obj != null) {
				Object[] objArr = (Object[]) obj;
				row = new Role();
				row.setId(objArr[0].toString());
				row.setRoleCode(objArr[1].toString());
				row.setRoleName(objArr[2].toString());
				row.setIsActive(Boolean.valueOf(objArr[3].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<Role> optional = Optional.ofNullable(row);
		return optional;
	}
}
