package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

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
	
}