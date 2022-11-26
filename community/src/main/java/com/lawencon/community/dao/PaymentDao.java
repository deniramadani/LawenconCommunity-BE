package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.dto.report.ReportResDto;
import com.lawencon.community.model.Payment;

@Repository
public class PaymentDao extends AbstractJpaDao{

	public Long countAllType(final String code) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT COUNT(tp.id) AS total_type ")
				.append("FROM tb_payment tp ")
				.append("INNER JOIN tb_product p ON p.id = tp.product_id ")
				.append("INNER JOIN tb_product_type tpt ON tpt.id = p.type_product_id ")
				.append("WHERE tpt.product_type_code = :code ");
		Long result = Long.valueOf(ConnHandler.getManager().createNativeQuery(query.toString()).setParameter("code", code).getSingleResult().toString());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Payment> getAllUserId(final Integer start, final Integer limit, final String userId)  {
		final StringBuilder query = new StringBuilder()
				.append("SELECT * ")
				.append("FROM tb_payment tp ")
				.append("INNER JOIN tb_user tu ON tu.id = tp.user_id ")
				.append("INNER JOIN tb_file tf ON tf.id = tp.transfer_photo_id ")
				.append("INNER JOIN tb_product p ON p.id = tp.product_id ")
				.append("INNER JOIN tb_user town ON town.id = p.owner_id ")
				.append("INNER JOIN tb_product_type tpt ON tpt.id = p.type_product_id ")
				.append("INNER JOIN tb_file tfoto ON tfoto.id = p.photo_id ")
				.append("WHERE tp.user_id = :userId ")
				.append("ORDER BY tp.created_at DESC ")
				.append("OFFSET :start LIMIT :limit ");
		final List<Payment> result = ConnHandler.getManager().createNativeQuery(query.toString(), Payment.class)
				.setParameter("userId", userId).setParameter("start", start).setParameter("limit", limit).getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Payment> getAllOwnerId(final Integer start, final Integer limit, final String ownerId)  {
		final StringBuilder query = new StringBuilder()
				.append("SELECT * ")
				.append("FROM tb_payment tp ")
				.append("INNER JOIN tb_user tu ON tu.id = tp.user_id ")
				.append("INNER JOIN tb_file tf ON tf.id = tp.transfer_photo_id ")
				.append("INNER JOIN tb_product p ON p.id = tp.product_id ")
				.append("INNER JOIN tb_user town ON town.id = p.owner_id ")
				.append("INNER JOIN tb_product_type tpt ON tpt.id = p.type_product_id ")
				.append("INNER JOIN tb_file tfoto ON tfoto.id = p.photo_id ")
				.append("WHERE p.owner_id = :ownerId ")
				.append("ORDER BY tp.created_at DESC ")
				.append("OFFSET :start LIMIT :limit ");
		final List<Payment> result = ConnHandler.getManager().createNativeQuery(query.toString(), Payment.class)
				.setParameter("ownerId", ownerId).setParameter("start", start).setParameter("limit", limit).getResultList();
		return result;
	}
	
	public Optional<Payment> getByTransactionCode(final String code) {
		final StringBuilder query = new StringBuilder()
			.append("SELECT id, transaction_code, approval, versions, is_active ")
			.append("FROM tb_payment tb ")
			.append("WHERE transaction_code = :code ");
		Payment row = null;
		try {
			final Object objCol = createNativeQuery(query.toString()).setParameter("code", code).getSingleResult();
			if (objCol != null) {
				Object[] objArr = (Object[]) objCol;
				row = new Payment();
				row.setId(objArr[0].toString());
				row.setTransactionCode(objArr[1].toString());
				row.setApproval(Boolean.valueOf(objArr[2].toString()));
				row.setVersion(Integer.valueOf(objArr[3].toString()));
				row.setIsActive(Boolean.valueOf(objArr[4].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<Payment> optional = Optional.ofNullable(row);
		return optional;
	}
	
	public List<ReportResDto> getProductivityMember(final String userId, final String startDate, final String endDate) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT ROW_NUMBER() OVER(), tpt.product_type_name, p.title, ts.date_time_start, COUNT(user_id) ")
				.append("FROM tb_payment tp ")
				.append("INNER JOIN tb_product p ON tp.product_id = p.id ")
				.append("INNER JOIN tb_product_type tpt ON p.type_product_id = tpt.id ")
				.append("INNER JOIN tb_schedule ts ON p.id = ts.product_id ")
				.append("INNER JOIN tb_user tu ON tp.user_id = tu.id ")
				.append("INNER JOIN tb_user town ON p.owner_id = town.id ")
				.append("WHERE tp.created_at >= DATE(:startDate) AND tp.created_at <= DATE(:endDate) ")
				.append("AND tp.approval = TRUE AND p.owner_id = :userId ")
				.append("GROUP BY tpt.product_type_name, p.title, ts.date_time_start ")
				.append("ORDER BY ts.date_time_start DESC, tpt.product_type_name ASC, p.title ASC ");
		final List<?> result = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("userId", userId).getResultList();
		final List<ReportResDto> data =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				Object[] objArr = (Object[]) objCol;
				final ReportResDto row = new ReportResDto();
				row.setNo(Long.valueOf(objArr[0].toString()));
				row.setType(objArr[1].toString());
				row.setTitle(objArr[2].toString());
				row.setStartDate(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime().toLocalDate());
				row.setTotalParticipants(Integer.valueOf(objArr[4].toString()));
				data.add(row);
			});
		}
		return data;
	}
	
	public List<ReportResDto> getRevenueMember(final String userId, final String startDate, final String endDate) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT ROW_NUMBER() OVER(), tpt.product_type_name, p.title, ts.date_time_start, (0.9*COUNT(user_id)*p.price) ")
				.append("FROM tb_payment tp ")
				.append("INNER JOIN tb_product p ON tp.product_id = p.id ")
				.append("INNER JOIN tb_product_type tpt ON p.type_product_id = tpt.id ")
				.append("INNER JOIN tb_schedule ts ON p.id = ts.product_id ")
				.append("INNER JOIN tb_user tu ON tp.user_id = tu.id ")
				.append("INNER JOIN tb_user town ON p.owner_id = town.id ")
				.append("WHERE tp.created_at >= DATE(:startDate) AND tp.created_at <= DATE(:endDate) ")
				.append("AND tp.approval = TRUE AND p.owner_id = :userId ")
				.append("GROUP BY tpt.product_type_name, p.title, ts.date_time_start, p.price ")
				.append("ORDER BY ts.date_time_start DESC, tpt.product_type_name ASC, p.title ASC ");
		final List<?> result = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("userId", userId).getResultList();
		final List<ReportResDto> data =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				Object[] objArr = (Object[]) objCol;
				final ReportResDto row = new ReportResDto();
				row.setNo(Long.valueOf(objArr[0].toString()));
				row.setType(objArr[1].toString());
				row.setTitle(objArr[2].toString());
				row.setStartDate(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime().toLocalDate());
				row.setTotalIncome(BigDecimal.valueOf(Double.valueOf(objArr[4].toString())));
				data.add(row);
			});
		}
		return data;
	}
	
	private String loopUserIdList(final List<String> userIdList) {
		final StringBuilder userIdQuery = new StringBuilder();
		for(int i = 0; i < userIdList.size(); i++) {
			userIdQuery.append("'");
			userIdQuery.append(userIdList.get(i));
			userIdQuery.append("'");
			if (i != userIdList.size()-1) {
				userIdQuery.append(", ");
			}
		}
		return userIdQuery.toString();
	}
	
	public List<ReportResDto> getProductivitySuperAdmin(final List<String> userIdList, final String startDate, final String endDate) {
		final String userIdQuery = loopUserIdList(userIdList);
		final StringBuilder query = new StringBuilder()
				.append("SELECT ROW_NUMBER() OVER(), town.fullname, p.provider, tpt.product_type_name, p.title, ts.date_time_start, COUNT(user_id) ")
				.append("FROM tb_payment tp ")
				.append("INNER JOIN tb_product p ON tp.product_id = p.id ")
				.append("INNER JOIN tb_product_type tpt ON p.type_product_id = tpt.id ")
				.append("INNER JOIN tb_schedule ts ON p.id = ts.product_id ")
				.append("INNER JOIN tb_user tu ON tp.user_id = tu.id ")
				.append("INNER JOIN tb_user town ON p.owner_id = town.id ")
				.append("WHERE tp.created_at >= DATE(:startDate) AND tp.created_at <= DATE(:endDate) AND tp.approval = TRUE ")
				.append("AND p.owner_id IN ").append(" (").append(userIdQuery).append(") ")
				.append("GROUP BY town.fullname, p.provider, tpt.product_type_name, p.title, ts.date_time_start ")
				.append("ORDER BY ts.date_time_start DESC, tpt.product_type_name ASC, p.title ASC ");
		final List<?> result = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
		final List<ReportResDto> data =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				Object[] objArr = (Object[]) objCol;
				final ReportResDto row = new ReportResDto();
				row.setNo(Long.valueOf(objArr[0].toString()));
				row.setMemberName(objArr[1].toString());
				row.setProvider(objArr[2].toString());
				row.setType(objArr[3].toString());
				row.setTitle(objArr[4].toString());
				row.setStartDate(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime().toLocalDate());
				row.setTotalParticipants(Integer.valueOf(objArr[6].toString()));
				data.add(row);
			});
		}
		return data;
	}
	
}