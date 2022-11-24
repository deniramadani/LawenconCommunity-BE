package com.lawencon.community.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportPojo {
	private Long no;
	private String type;
	private String title;
	private LocalDate startDate;
	private Integer totalParticipants;
	private String memberName;
	private String provider;
	private BigDecimal totalIncome;
}
