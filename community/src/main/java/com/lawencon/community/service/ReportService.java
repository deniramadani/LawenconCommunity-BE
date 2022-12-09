package com.lawencon.community.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dto.report.ReportCountResDto;
import com.lawencon.community.dto.report.ReportResDto;

@Service
public class ReportService extends BaseCoreService {
	
	@Autowired
	private PaymentDao paymentDao;
	
	public List<ReportResDto> getProductivityMember(final String userId, final String startDate, final String endDate){
		return paymentDao.getProductivityMember(userId, startDate, endDate);
	}
	
	public List<ReportResDto> getRevenueMember(final String userId, final String startDate, final String endDate){
		return paymentDao.getRevenueMember(userId, startDate, endDate);
	}
	
	public List<ReportResDto> getAllProductivityMember(final String userId, final Integer start, final Integer limit){
		return paymentDao.getAllProductivityMember(userId, start, limit);
	}
	
	public List<ReportResDto> getAllRevenueMember(final String userId, final Integer start, final Integer limit){
		return paymentDao.getAllRevenueMember(userId, start, limit);
	}
	
	public List<ReportResDto> getProductivitySuperAdmin(final List<String> userIdList, final String startDate, final String endDate){
		return paymentDao.getProductivitySuperAdmin(userIdList, startDate, endDate);
	}
	
	public List<ReportResDto> getRevenueSuperAdmin(final List<String> userIdList, final String startDate, final String endDate){
		return paymentDao.getRevenueSuperAdmin(userIdList, startDate, endDate);
	}
	
	public String formatDateRange(final String startDate, final String endDate) throws Exception{
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		final SimpleDateFormat display = new SimpleDateFormat("dd MMM yyyy");
		final StringBuilder dateRange = new StringBuilder()
				.append(display.format(formatter.parse(startDate)))
				.append(" - ")
				.append(display.format(formatter.parse(endDate)));
		return dateRange.toString();
	}
	
	public List<ReportResDto> getProductivitySuperAdminData(final String startDate, final String endDate){
		return paymentDao.getProductivitySuperAdminData(startDate, endDate);
	}
	
	public List<ReportResDto> getRevenueSuperAdminData(final String startDate, final String endDate){
		return paymentDao.getRevenueSuperAdminData(startDate, endDate);
	}
	
	public List<ReportResDto> getAllProductivitySuperAdmin(){
		return paymentDao.getAllProductivitySuperAdmin();
	}
	
	public List<ReportResDto> getAllRevenueSuperAdmin(){
		return paymentDao.getAllRevenueSuperAdmin();
	}
	
	public ReportCountResDto getCountProductivityMember(final String userId){
		final Long totalRow = paymentDao.getCountProductivityMember(userId);
		final ReportCountResDto res = new ReportCountResDto();
		res.setTotalRow(totalRow);
		return res;
	}
	
	public ReportCountResDto getCountRevenueMember(final String userId){
		final Long totalRow = paymentDao.getCountRevenueMember(userId);
		final ReportCountResDto res = new ReportCountResDto();
		res.setTotalRow(totalRow);
		return res;
	}
	
}
