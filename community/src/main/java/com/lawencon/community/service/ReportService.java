package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.pojo.ReportPojo;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ReportService extends BaseCoreService {
	
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private PrincipalService principalService;
	
	public List<ReportPojo> getProductivityMember(){
		return paymentDao.getProductivityMember(principalService.getAuthPrincipal());
	}
	
}
