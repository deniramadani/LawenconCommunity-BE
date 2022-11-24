package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.RoleConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.dashboard.DashboardAdminDto;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.User;

@Service
public class DashboardService extends BaseCoreService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IndustryDao industryDao;

	@Autowired
	private PositionDao positionDao;

	public DashboardAdminDto getAll() {
		final DashboardAdminDto dashboardDto = new DashboardAdminDto();
		final Long userTotal = userDao.countAll(User.class);
		final String wherePremium = "WHERE userType.userTypeCode = :userType AND role.roleCode = :roleCode";
		final String[] paramNamePremium =  {"userType", "roleCode"};
		final String[] paramValuePremium =  {UserTypeConst.PREMIUM.getUserTypeCodeEnum(), RoleConst.MEMBER.getRoleCodeEnum()};
		final Long totalPremiumMember = userDao.countAll(User.class, wherePremium, paramNamePremium, paramValuePremium);
		final String whereBasic = "WHERE userType.userTypeCode = :userType AND role.roleCode = :roleCode";
		final String[] paramNameBasic =  {"userType", "roleCode"};
		final String[] paramValueBasic =  {UserTypeConst.BASIC.getUserTypeCodeEnum(), RoleConst.MEMBER.getRoleCodeEnum()};
		final Long totalBasicMember = userDao.countAll(User.class, whereBasic, paramNameBasic, paramValueBasic);
		final String whereAdmin = "WHERE role.roleCode = :roleCode";
		final String[] paramNameAdmin =  {"roleCode"};
		final String[] paramValueAdmin =  { RoleConst.ADMIN.getRoleCodeEnum()};
		final Long totalAdmin = userDao.countAll(User.class, whereAdmin, paramNameAdmin, paramValueAdmin);
		
		final Long industryTotal = industryDao.countAll(Industry.class);
		final Long positionTotal = positionDao.countAll(Position.class);

		dashboardDto.setIndustryTotal(industryTotal);
		dashboardDto.setPositionTotal(positionTotal);
		dashboardDto.setUserTotal(userTotal);
		dashboardDto.setMemberPremiumTotal(totalPremiumMember);
		dashboardDto.setAdminTotal(totalAdmin);
		dashboardDto.setMemberBasicTotal(totalBasicMember);
		return dashboardDto;
	}

}
