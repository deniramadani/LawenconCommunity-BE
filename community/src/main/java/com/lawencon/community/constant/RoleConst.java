package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum RoleConst {
	
	SYSTEM("ROLSY", "System"), SUPERADMIN("ROLSA", "Super Admin"),
	ADMIN("ROLAM", "Admin"), MEMBER("ROLMM", "Member");

	private final String roleCodeEnum;
	private final String roleNameEnum;
	
	RoleConst(final String roleCodeEnum, final String roleNameEnum) {
		this.roleCodeEnum = roleCodeEnum;
		this.roleNameEnum = roleNameEnum;
	}

}
