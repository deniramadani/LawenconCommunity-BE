package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
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
	
}