package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ReportConst {
	
	PRODUCTIVITY_MEMBER("Participant", "Member Report"), REVENUE_MEMBER("Revenue", "Member Report"),
	PRODUCTIVITY_SUPERADMIN("Participant", "Super Admin Report"), REVENUE_SUPERADMIN("Revenue", "Super Admin Report");

	private final String reportTitleEnum;
	private final String reportTypeEnum;
	
	ReportConst(final String reportTitleEnum, final String reportTypeEnum) {
		this.reportTitleEnum = reportTitleEnum;
		this.reportTypeEnum = reportTypeEnum;
	}
	
}