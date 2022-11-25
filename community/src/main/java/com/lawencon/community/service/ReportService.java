package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.pojo.ReportPojo;

@Service
public class ReportService extends BaseCoreService {
	
	@Autowired
	private PaymentDao paymentDao;
	
	public List<ReportPojo> getProductivityMember(final String userId, final String startDate, final String endDate){
		return paymentDao.getProductivityMember(userId, startDate, endDate);
	}
	
	public List<ReportPojo> getRevenueMember(final String userId, final String startDate, final String endDate){
		return paymentDao.getRevenueMember(userId, startDate, endDate);
	}
	
}
