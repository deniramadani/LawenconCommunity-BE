package com.lawencon.community.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.pojo.ReportPojo;
import com.lawencon.community.service.ReportService;
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
	private PaymentDao paymentDao;
	@Autowired
	private PrincipalService principalService;

	@GetMapping("productivity-member")
	public ResponseEntity<?> reportSample() throws Exception {
		List<ReportPojo> data = reportService.getProductivityMember();
		Map<String, Object> map = new HashMap<>();
		map.put("company", "PT. Company Internasional");
		byte[] out = jasperUtil.responseToByteArray(data, map, "productivity.member.report");
		String fileName = "report.pdf";
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	}
	
	@GetMapping
	public ResponseEntity<List<ReportPojo>> getAll(){
		List<ReportPojo> result = new ArrayList<>(); 
		result = paymentDao.getProductivityMember(principalService.getAuthPrincipal());						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
}
