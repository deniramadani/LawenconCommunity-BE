package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.dashboard.DashboardDto;
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

	public DashboardDto getAll() {
		final DashboardDto dashboardDto = new DashboardDto();
		final Long userTotal = userDao.countAll(User.class);
		final Long industryTotal = industryDao.countAll(Industry.class);
		final Long positionTotal = positionDao.countAll(Position.class);

		dashboardDto.setIndustryTotal(industryTotal);
		dashboardDto.setPositionTotal(positionTotal);
		dashboardDto.setUserTotal(userTotal);
		return dashboardDto;
	}

}
