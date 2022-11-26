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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.ReportConst;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dto.report.ReportReqDto;
import com.lawencon.community.dto.report.ReportResDto;
import com.lawencon.community.model.User;
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
	
//	@GetMapping
//	public ResponseEntity<List<ReportResDto>> getAll(){
//		List<ReportResDto> result = new ArrayList<>(); 
//		result = paymentDao.getRevenueMember(principalService.getAuthPrincipal(), "2022-10-01", "2022-11-30");						
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
	
	@GetMapping
	public ResponseEntity<List<ReportResDto>> getAll(@RequestBody final ReportReqDto request){
		List<ReportResDto> result = new ArrayList<>(); 
		result = paymentDao.getProductivitySuperAdmin(request.getUserId(), request.getStartDate(), request.getEndDate());						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@GetMapping("productivity-member")
	public ResponseEntity<?> productivityMember(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportResDto> data = reportService.getProductivityMember(user.getId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.PRODUCTIVITY_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.PRODUCTIVITY_MEMBER.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(request.getStartDate())))
				.append(" - ")
				.append(display.format(formatter.parse(request.getEndDate())));
		map.put("dateRange", dateRange.toString());
		final byte[] out = jasperUtil.responseToByteArray(data, map, "productivity.member.report");
		final String fileName = "productivity.member.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@GetMapping("revenue-member")
	public ResponseEntity<?> revenueMember(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportResDto> data = reportService.getRevenueMember(user.getId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.REVENUE_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.REVENUE_MEMBER.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(request.getStartDate())))
				.append(" - ")
				.append(display.format(formatter.parse(request.getEndDate())));
		map.put("dateRange", dateRange.toString());
		final byte[] out = jasperUtil.responseToByteArray(data, map, "revenue.member.report");
		final String fileName = "revenue.member.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@GetMapping("productivity-super_admin")
	public ResponseEntity<?> productivitySuperAdmin(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportResDto> data = reportService.getProductivitySuperAdmin(request.getUserId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.PRODUCTIVITY_SUPERADMIN.getReportTitleEnum());
		map.put("reportType", ReportConst.PRODUCTIVITY_SUPERADMIN.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(request.getStartDate())))
				.append(" - ")
				.append(display.format(formatter.parse(request.getEndDate())));
		map.put("dateRange", dateRange.toString());
		final byte[] out = jasperUtil.responseToByteArray(data, map, "productivity.super-admin.report");
		final String fileName = "productivity.super-admin.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
}
