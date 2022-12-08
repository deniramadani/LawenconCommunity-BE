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
import com.lawencon.community.dto.report.ReportCountResDto;
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
	
	private final String companyName = "PT Lawencon Internasional";
	private final String companyAddress = "Pakuwon Tower, Jl. Casablanca No.Kav 88, RT.6/RW.14, Kb. Baru, Kec. Tebet, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12870";
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("productivity/member")
	public ResponseEntity<?> getProductivityMember(@RequestBody final ReportReqDto request) throws Exception {
		final User user = userService.getById(principalService.getAuthPrincipal());
		if(request.getEndDate() == null) {request.setEndDate(request.getStartDate());}
		final List<ReportResDto> data = reportService.getProductivityMember(user.getId(), request.getStartDate(), request.getEndDate());
		final Map<String, Object> map = new HashMap<>();
		map.put("reportTitle", ReportConst.PRODUCTIVITY_MEMBER.getReportTitleEnum());
		map.put("reportType", ReportConst.PRODUCTIVITY_MEMBER.getReportTypeEnum());
		map.put("memberEmail", user.getEmail());
		map.put("companyName", companyName);
		map.put("companyAddress", companyAddress);
		final String dateRange = reportService.formatDateRange(request.getStartDate(), request.getEndDate());
		map.put("dateRange", dateRange);
		final byte[] out = jasperUtil.responseToByteArray(data, map, "participant.member.report");
		final String fileName = "participant.member.report.pdf";
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
		map.put("reportType", ReportConst.REVENUE_MEMBER.getReportTypeEnum());
		map.put("memberEmail", user.getEmail());
		map.put("companyName", companyName);
		map.put("companyAddress", companyAddress);
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
		map.put("reportType", ReportConst.PRODUCTIVITY_SUPERADMIN.getReportTypeEnum());
		map.put("memberEmail", user.getEmail());
		map.put("companyName", companyName);
		map.put("companyAddress", companyAddress);
		final String dateRange = reportService.formatDateRange(request.getStartDate(), request.getEndDate());
		map.put("dateRange", dateRange);
		final byte[] out = jasperUtil.responseToByteArray(data, map, "participant.super-admin.report");
		final String fileName = "participant.super-admin.report.pdf";
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
		map.put("reportType", ReportConst.REVENUE_SUPERADMIN.getReportTypeEnum());
		map.put("memberEmail", user.getEmail());
		map.put("companyName", companyName);
		map.put("companyAddress", companyAddress);
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
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@GetMapping("productivity/super-admin/data-all")
	public ResponseEntity<List<ReportResDto>> getAllProductivitySuperAdmin() {
		final List<ReportResDto> result = reportService.getAllProductivitySuperAdmin();
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
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@GetMapping("revenue/super-admin/data-all")
	public ResponseEntity<?> getAllRevenueSuperAdminData() {
		final List<ReportResDto> result = reportService.getAllRevenueSuperAdmin();
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
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@GetMapping("productivity/member/count")
	public ResponseEntity<ReportCountResDto> getCountProductivityMember() {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final ReportCountResDto result = reportService.getCountProductivityMember(user.getId());					
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@GetMapping("revenue/member/count")
	public ResponseEntity<ReportCountResDto> getCountRevenueMember() {
		final User user = userService.getById(principalService.getAuthPrincipal());
		final ReportCountResDto result = reportService.getCountRevenueMember(user.getId());					
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
