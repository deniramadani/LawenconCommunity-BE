package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserType;

@Repository
public class UserDao extends AbstractJpaDao {

	public Optional<User> getByEmail(final String email) {
		final StringBuilder query = new StringBuilder()
			.append("SELECT tb_user.id AS id, email, password, fullname, role_code, role_name, ")
			.append("user_type_code, user_type_name, tb_position.position_name AS p_name, tb_file.id AS f_id , ")
			.append("tb_user.is_active, tb_user.versions ")
			.append("FROM tb_user ")
			.append("INNER JOIN tb_role ON tb_user.role_id = tb_role.id ")
			.append("INNER JOIN tb_user_type ON tb_user.user_type_id = tb_user_type.id ")
			.append("LEFT JOIN tb_position ON tb_user.position_id = tb_position.id ")
			.append("LEFT JOIN tb_file ON tb_user.photo_id = tb_file.id ")
			.append("WHERE email = :email AND tb_user.is_active = true");
		User row = null;
		try {
			final Object userObj = createNativeQuery(query.toString()).setParameter("email", email).getSingleResult();
			if (userObj != null) {
				final Object[] objArr = (Object[]) userObj;
				row = new User();
				final Role role = new Role();
				final UserType userType = new UserType();
				final Position position = new Position();
				final File file = new File();
				row.setId(objArr[0].toString());
				row.setEmail(objArr[1].toString());
				row.setPassword(objArr[2].toString());
				row.setFullname(objArr[3].toString());
				role.setRoleCode(objArr[4].toString());
				role.setRoleName(objArr[5].toString());
				userType.setUserTypeCode(objArr[6].toString());
				userType.setUserTypeName(objArr[7].toString());
				if(objArr[8] != null) {
					position.setPositionName(objArr[8].toString());
					row.setPosition(position);
				}
				if(objArr[9] != null) {
					file.setId(objArr[9].toString());
					row.setPhoto(file);
				}
				row.setIsActive(Boolean.valueOf(objArr[10].toString()));
				row.setVersion(Integer.valueOf(objArr[11].toString()));
				row.setRole(role);
				row.setUserType(userType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Optional<User> optional = Optional.ofNullable(row);
		return optional;
	}

}
