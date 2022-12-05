package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.RoleConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.dashboard.DashboardAdminDto;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class DashboardService extends BaseCoreService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private IndustryDao industryDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private PrincipalService principalService;

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

		final String whereSuperAdmin = "WHERE role.roleCode = :roleCode";
		final String[] paramNameSuperAdmin =  {"roleCode"};
		final String[] paramValueSuperAdmin =  { RoleConst.SUPERADMIN.getRoleCodeEnum()};
		final Long totalSuperAdmin = userDao.countAll(User.class, whereSuperAdmin, paramNameSuperAdmin, paramValueSuperAdmin);
		
		final String whereMember = "WHERE role.roleCode = :roleCode";
		final String[] paramNameMember =  {"roleCode"};
		final String[] paramValueMember =  { RoleConst.MEMBER.getRoleCodeEnum()};
		final Long totalMember = userDao.countAll(User.class, whereMember, paramNameMember, paramValueMember);
		
		
		final String whereArticleByAdmin = "WHERE createdBy = :userId";
		final String[] paramNameArticleByAdmin =  {"userId"};
		final String[] paramValueArticleByAdmin =  { principalService.getAuthPrincipal() };
		final Long totalArticleByAdmin = articleDao.countAll(Article.class, whereArticleByAdmin, paramNameArticleByAdmin, paramValueArticleByAdmin);
		
		final String whereApprovedPayment = "WHERE approval = true AND isActive = true";
		final String[] paramNameApprovedPayment =  { };
		final String[] paramValueApprovedPayment =  {  };
		final Long totalApprovedPayment = paymentDao.countAll(Payment.class, whereApprovedPayment, paramNameApprovedPayment, paramValueApprovedPayment);
		
		final String whereRejectedPayment = "WHERE approval = false AND isActive = false";
		final String[] paramNameRejectedPayment =  { };
		final String[] paramValueRejectedPayment =  {  };
		final Long totalRejectedPayment = paymentDao.countAll(Payment.class, whereRejectedPayment, paramNameRejectedPayment, paramValueRejectedPayment);
		
		final String wherePendingPayment = "WHERE approval = false AND isActive = true";
		final String[] paramNamePendingPayment =  { };
		final String[] paramValuePendingPayment =  {  };
		final Long totalPendingPayment = paymentDao.countAll(Payment.class, wherePendingPayment, paramNamePendingPayment, paramValuePendingPayment);
		
		final Long industryTotal = industryDao.countAll(Industry.class);
		final Long positionTotal = positionDao.countAll(Position.class);
		final Long paymentTotal = paymentDao.countAll(Payment.class);
		final Long articleTotal = articleDao.countAll(Article.class);
		final Long postTotal = postDao.countAll(Post.class);

		dashboardDto.setIndustryTotal(industryTotal);
		dashboardDto.setPositionTotal(positionTotal);
		dashboardDto.setUserTotal(userTotal);
		dashboardDto.setMemberPremiumTotal(totalPremiumMember);
		dashboardDto.setAdminTotal(totalAdmin);
		dashboardDto.setMemberBasicTotal(totalBasicMember);
		dashboardDto.setArticleByAdminTotal(totalArticleByAdmin);
		dashboardDto.setPaymentTotal(paymentTotal);
		dashboardDto.setApprovedPaymentTotal(totalApprovedPayment);
		dashboardDto.setPendingPaymentTotal(totalPendingPayment);
		dashboardDto.setRejectedPaymentTotal(totalRejectedPayment);
		dashboardDto.setArticleTotal(articleTotal);
		dashboardDto.setPostTotal(postTotal);
		dashboardDto.setSuperadminTotal(totalSuperAdmin);
		dashboardDto.setMemberTotal(totalMember);
		return dashboardDto;
	}

}
