package com.lawencon.community.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportResDto {
	private Long no;
	private String type;
	private String title;
	private LocalDate startDate;
	private Integer totalParticipants;
	private String memberName;
	private String provider;
	private BigDecimal totalIncome;
}
