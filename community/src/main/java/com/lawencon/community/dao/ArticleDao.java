package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;

@Repository
public class ArticleDao extends AbstractJpaDao {
	
	public List<Article> getAllArticle(final Integer start, final Integer limit) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT ta.id, ta.title, ta.content, ta.photo_id, ta.created_by, ")
				.append("tu.fullname, ta.created_at, ta.versions, ta.is_active ")
				.append("FROM tb_article ta ")
				.append("INNER JOIN tb_file tf ON tf.id = ta.photo_id ")
				.append("INNER JOIN tb_user tu ON tu.id = ta.created_by ")
				.append("WHERE ta.is_active = true ")
				.append("ORDER BY ta.created_at DESC ");
		final List<?> result = createNativeQuery(query.toString(), start, limit).getResultList();
		final List<Article> rows =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				final Object[] objArr = (Object[]) objCol;
				final Article row = new Article();
				row.setId(objArr[0].toString());
				row.setTitle(objArr[1].toString());
				row.setContent(objArr[2].toString());
				final File file = new File();
				file.setId(objArr[3].toString());
				row.setFile(file);
				row.setCreatedBy(objArr[4].toString());
				final User user = new User();
				user.setFullname(objArr[5].toString());
				row.setUser(user);
				row.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				row.setVersion(Integer.valueOf(objArr[7].toString()));
				row.setIsActive(Boolean.valueOf(objArr[8].toString()));
				rows.add(row);
			});
		}
		return rows;
	}
	
	public List<Article> getAllByUserId(final Integer start, final Integer limit, final String userId) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT ta.id, ta.title, ta.content, ta.photo_id, ta.created_by, ")
				.append("tu.fullname, ta.created_at, ta.versions, ta.is_active ")
				.append("FROM tb_article ta ")
				.append("INNER JOIN tb_file tf ON tf.id = ta.photo_id ")
				.append("INNER JOIN tb_user tu ON tu.id = ta.created_by ")
				.append("WHERE ta.is_active = true AND ta.created_by = :userId ")
				.append("ORDER BY ta.created_at DESC ");
		final List<?> result = createNativeQuery(query.toString(), start, limit).setParameter("userId", userId).getResultList();
		final List<Article> rows =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				final Object[] objArr = (Object[]) objCol;
				final Article row = new Article();
				row.setId(objArr[0].toString());
				row.setTitle(objArr[1].toString());
				row.setContent(objArr[2].toString());
				final File file = new File();
				file.setId(objArr[3].toString());
				row.setFile(file);
				row.setCreatedBy(objArr[4].toString());
				final User user = new User();
				user.setFullname(objArr[5].toString());
				row.setUser(user);
				row.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				row.setVersion(Integer.valueOf(objArr[7].toString()));
				row.setIsActive(Boolean.valueOf(objArr[8].toString()));
				rows.add(row);
			});
		}
		return rows;
	}
	
}
