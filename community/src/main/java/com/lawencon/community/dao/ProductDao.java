package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Product;
import com.lawencon.community.model.ProductType;

@Repository
public class ProductDao extends AbstractJpaDao {
	
	public Optional<Product> getSubscribeId(final String productTypeCode) {
		final StringBuilder query = new StringBuilder()
			.append("SELECT tp.id, tpt.product_type_code ")
			.append("FROM tb_product tp ")
			.append("INNER JOIN tb_product_type tpt ON tpt.id = tp.type_product_id ")
			.append("WHERE tpt.product_type_code = :productTypeCode ");
		Product row = null;
		try {
			final Object userObj = createNativeQuery(query.toString()).setParameter("productTypeCode", productTypeCode).getSingleResult();
			if (userObj != null) {
				final Object[] objArr = (Object[]) userObj;
				row = new Product();
				final ProductType productType = new ProductType();
				row.setId(objArr[0].toString());
				productType.setProductTypeCode(objArr[1].toString());
				row.setProductType(productType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<Product> optional = Optional.ofNullable(row);
		return optional;
	}
	
}
