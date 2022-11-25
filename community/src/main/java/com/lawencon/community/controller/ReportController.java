package com.lawencon.community.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.ReportConst;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.ReportPojo;
import com.lawencon.community.service.ReportService;
import com.lawencon.community.service.UserService;
import com.lawencon.security.principal.PrincipalService;
import com.lawencon.util.JasperUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
	@Autowired
	private JasperUtil jasperUtil;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@GetMapping
	public ResponseEntity<List<ReportPojo>> getAll(){
		List<ReportPojo> result = new ArrayList<>(); 
		result = paymentDao.getRevenueMember(principalService.getAuthPrincipal(), "2022-10-01", "2022-11-30");						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("productivity-member")
	public ResponseEntity<?> productivityMember(@RequestParam(required = true) final String startDate,
			@RequestParam(required = true) final String endDate) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportPojo> data = reportService.getProductivityMember(user.getId(), startDate, endDate);
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.PRODUCTIVITY_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.PRODUCTIVITY_MEMBER.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(startDate)))
				.append(" - ")
				.append(display.format(formatter.parse(endDate)));
		map.put("dateRange", dateRange.toString());
		final byte[] out = jasperUtil.responseToByteArray(data, map, "productivity.member.report");
		final String fileName = "report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@GetMapping("revenue-member")
	public ResponseEntity<?> revenueMember(@RequestParam(required = true) final String startDate,
			@RequestParam(required = true) final String endDate) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportPojo> data = reportService.getRevenueMember(user.getId(), startDate, endDate);
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.REVENUE_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.REVENUE_MEMBER.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(startDate)))
				.append(" - ")
				.append(display.format(formatter.parse(endDate)));
		map.put("dateRange", dateRange.toString());
		final byte[] out = jasperUtil.responseToByteArray(data, map, "revenue.member.report");
		final String fileName = "report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
}
