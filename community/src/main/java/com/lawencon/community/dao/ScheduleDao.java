package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Product;
import com.lawencon.community.model.ProductType;
import com.lawencon.community.model.Schedule;
import com.lawencon.community.model.User;

@Repository
public class ScheduleDao extends AbstractJpaDao{
	
	public Optional<Schedule> getByIdProduct(final String productId) {
		final StringBuilder query = new StringBuilder()
			.append("SELECT ts.id AS ts_id, ts.date_time_start, ts.date_time_end, ")
			.append("ts.created_by, ts.created_at, ts.versions AS ts_versions, ts.is_active AS ts_is_active, ")
			.append("tp.id AS tp_id, tp.title, tp.content, tp.provider, tp.location, ")
			.append("tp.price, tp.versions AS tp_versions, tp.is_active AS tp_is_active, ")
			.append("tu.id AS tu_id, tu.fullname, ")
			.append("tpt.id AS tpt_id, tpt.product_type_name, tf.id AS tf_id ")
			.append("FROM tb_schedule ts ")
			.append("INNER JOIN tb_product tp ON tp.id = ts.product_id ")
			.append("INNER JOIN tb_user tu ON tp.owner_id = tu.id ")
			.append("INNER JOIN tb_product_type tpt ON tp.type_product_id = tpt.id ")
			.append("INNER JOIN tb_file tf ON tp.photo_id = tf.id ")
			.append("WHERE tp.id = :productId AND ts.is_active = true");
		Schedule row = null;
		try {
			final Object userObj = createNativeQuery(query.toString()).setParameter("productId", productId).getSingleResult();
			if (userObj != null) {
				final Object[] objArr = (Object[]) userObj;
				row = new Schedule();
				row.setId(objArr[0].toString());
				row.setDateTimeStart(Timestamp.valueOf(objArr[1].toString()).toLocalDateTime());
				row.setDateTimeEnd(Timestamp.valueOf(objArr[2].toString()).toLocalDateTime());
				row.setCreatedBy(objArr[3].toString());
				row.setCreatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				row.setVersion(Integer.valueOf(objArr[5].toString()));
				row.setIsActive(Boolean.valueOf(objArr[6].toString()));
				final Product product = new Product();
				product.setId(objArr[7].toString());
				product.setTitle(objArr[8].toString());
				product.setContent(objArr[9].toString());
				product.setProvider(objArr[10].toString());
				product.setLocation(objArr[11].toString());
				product.setPrice(BigDecimal.valueOf(Double.valueOf(objArr[12].toString())));
				product.setVersion(Integer.valueOf(objArr[13].toString()));
				product.setIsActive(Boolean.valueOf(objArr[14].toString()));
				final User user = new User();
				user.setId(objArr[15].toString());
				user.setFullname(objArr[16].toString());
				product.setOwnerId(user);
				final ProductType productType = new ProductType();
				productType.setId(objArr[17].toString());
				productType.setProductTypeName(objArr[18].toString());
				product.setProductType(productType);
				final File file = new File();
				file.setId(objArr[19].toString());
				product.setPhoto(file);
				row.setProduct(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		final Optional<Schedule> optional = Optional.ofNullable(row);
		return optional;
	}
	
	@SuppressWarnings("unchecked")
	public List<Schedule> getAllSchedule(final Integer start, final Integer limit, final String code){
		final StringBuilder query = new StringBuilder()
				.append("SELECT * ")
				.append("FROM tb_schedule ts ")
				.append("INNER JOIN tb_product tp ON tp.id = ts.product_id ")
				.append("INNER JOIN tb_user tu ON tp.owner_id = tu.id ")
				.append("INNER JOIN tb_product_type tpt ON tp.type_product_id = tpt.id ")
				.append("INNER JOIN tb_file tf ON tp.photo_id = tf.id ")
				.append("WHERE ts.is_active = true AND tpt.product_type_code = :code ")
				.append("ORDER BY ts.created_at DESC ")
				.append("OFFSET :start LIMIT :limit ");
		final List<Schedule> result = ConnHandler.getManager().createNativeQuery(query.toString(), Schedule.class)
				.setParameter("code", code).setParameter("start", start).setParameter("limit", limit).getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Schedule> getAllByUserId(final Integer start, final Integer limit, final String userId, final String code){
		final StringBuilder query = new StringBuilder()
				.append("SELECT * ")
				.append("FROM tb_schedule ts ")
				.append("INNER JOIN tb_product tp ON tp.id = ts.product_id ")
				.append("INNER JOIN tb_user tu ON tp.owner_id = tu.id ")
				.append("INNER JOIN tb_product_type tpt ON tp.type_product_id = tpt.id ")
				.append("INNER JOIN tb_file tf ON tp.photo_id = tf.id ")
				.append("WHERE tu.id = :userId AND ts.is_active = true AND tpt.product_type_code = :code ")
				.append("ORDER BY ts.created_at DESC ")
				.append("OFFSET :start LIMIT :limit ");
		final List<Schedule> result = ConnHandler.getManager().createNativeQuery(query.toString(), Schedule.class)
				.setParameter("userId", userId).setParameter("code", code)
				.setParameter("start", start).setParameter("limit", limit).getResultList();
		return result;
	}
	
}