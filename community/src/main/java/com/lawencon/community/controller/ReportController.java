package com.lawencon.community.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.ReportConst;
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
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("productivity/member")
	public ResponseEntity<?> getProductivityMember(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> data = reportService.getProductivityMember(user.getId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.PRODUCTIVITY_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.PRODUCTIVITY_MEMBER.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final String dateRange = reportService.formatDateRange(request.getStartDate(), request.getEndDate());
		map.put("dateRange", dateRange);
		final byte[] out = jasperUtil.responseToByteArray(data, map, "productivity.member.report");
		final String fileName = "productivity.member.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("revenue/member")
	public ResponseEntity<?> getRevenueMember(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> data = reportService.getRevenueMember(user.getId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.REVENUE_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.REVENUE_MEMBER.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final String dateRange = reportService.formatDateRange(request.getStartDate(), request.getEndDate());
		map.put("dateRange", dateRange);
		final byte[] out = jasperUtil.responseToByteArray(data, map, "revenue.member.report");
		final String fileName = "revenue.member.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping("productivity/super-admin")
	public ResponseEntity<?> getProductivitySuperAdmin(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> data = reportService.getProductivitySuperAdmin(request.getUserId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.PRODUCTIVITY_SUPERADMIN.getReportTitleEnum());
		map.put("reportType", ReportConst.PRODUCTIVITY_SUPERADMIN.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final String dateRange = reportService.formatDateRange(request.getStartDate(), request.getEndDate());
		map.put("dateRange", dateRange);
		final byte[] out = jasperUtil.responseToByteArray(data, map, "productivity.super-admin.report");
		final String fileName = "productivity.super-admin.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping("revenue/super-admin")
	public ResponseEntity<?> getRevenueSuperAdmin(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> data = reportService.getRevenueSuperAdmin(request.getUserId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.REVENUE_SUPERADMIN.getReportTitleEnum());
		map.put("reportType", ReportConst.REVENUE_SUPERADMIN.getReportTypeEnum()+user.getEmail());
		map.put("company", user.getCompany());
		final String dateRange = reportService.formatDateRange(request.getStartDate(), request.getEndDate());
		map.put("dateRange", dateRange);
		final byte[] out = jasperUtil.responseToByteArray(data, map, "revenue.super-admin.report");
		final String fileName = "revenue.super-admin.report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("productivity/member/data")
	public ResponseEntity<List<ReportResDto>> getProductivityMemberData(@RequestBody final ReportReqDto request) {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> result = reportService.getProductivityMember(user.getId(), request.getStartDate(), request.getEndDate());					
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("revenue/member/data")
	public ResponseEntity<List<ReportResDto>> getRevenueMemberData(@RequestBody final ReportReqDto request) {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> result = reportService.getRevenueMember(user.getId(), request.getStartDate(), request.getEndDate());					
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//DISTINCT
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping("productivity/super-admin/data")
	public ResponseEntity<List<ReportResDto>> getProductivitySuperAdminData(@RequestBody final ReportReqDto request) {
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> result = reportService.getProductivitySuperAdminData(request.getStartDate(), request.getEndDate());
		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	//DISTINCT
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping("revenue/super-admin/data")
	public ResponseEntity<?> getRevenueSuperAdminData(@RequestBody final ReportReqDto request) {
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> result = reportService.getRevenueSuperAdminData(request.getStartDate(), request.getEndDate());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@GetMapping("productivity/member/data-all")
	public ResponseEntity<List<ReportResDto>> getAllProductivityMemberData(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit) {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportResDto> result = reportService.getAllProductivityMember(user.getId(), start, limit);					
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@GetMapping("revenue/member/data-all")
	public ResponseEntity<List<ReportResDto>> getAllRevenueMemberData(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit) {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final List<ReportResDto> result = reportService.getAllRevenueMember(user.getId(), start, limit);					
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
