package com.lawencon.community.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.ProductType;

@Repository
public class ProductTypeDao extends AbstractJpaDao{
	public Optional<ProductType> getByCode(final String productTypeCode) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT id, product_type_code, product_type_name, is_active, versions ")
				.append("FROM tb_product_type ")
				.append("WHERE product_type_code = :productTypeCode AND is_active = true ");
		ProductType row = null;
		try {
			final Object obj = createNativeQuery(query.toString()).setParameter("productTypeCode", productTypeCode).getSingleResult();
			if (obj != null) {
				Object[] objArr = (Object[]) obj;
				row = new ProductType();
				row.setId(objArr[0].toString());
				row.setProductTypeCode(objArr[1].toString());
				row.setProductTypeName(objArr[2].toString());
				row.setIsActive(Boolean.valueOf(objArr[3].toString()));
				row.setVersion(Integer.valueOf(objArr[4].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<ProductType> optional = Optional.ofNullable(row);
		return optional;
	}
}
