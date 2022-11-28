package com.lawencon.community.dto.report;

import java.util.List;

import lombok.Data;

@Data
public class ReportReqDto {
	
	private String startDate;
	private String endDate;
	private List<String> userId;
	
}
