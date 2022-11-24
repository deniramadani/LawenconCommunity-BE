package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ReportConst {
	PRODUCTIVITY_MEMBER("Productivity", "Member Report"), PRODUCTIVITY_SUPERADMIN("Productivity", "Super Admin Report");

	private final String reportTitleEnum;
	private final String reportTypeEnum;
	
	ReportConst(final String reportTitleEnum, final String reportTypeEnum) {
		this.reportTitleEnum = reportTitleEnum;
		this.reportTypeEnum = reportTypeEnum;
	}
}
