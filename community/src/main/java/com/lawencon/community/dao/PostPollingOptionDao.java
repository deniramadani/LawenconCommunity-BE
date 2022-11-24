package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostPolling;
import com.lawencon.community.model.PostPollingOption;

@Repository
public class PostPollingOptionDao extends AbstractJpaDao{


	public List<PostPollingOption> getAllByPostPolling(String id) {
		final List<PostPollingOption> postPollingOptions = new ArrayList<>();
		final StringBuilder query = new StringBuilder()
				.append("SELECT tppo.id, tppo.post_polling_id, tppo.content, tppo.created_by, tppo.created_at, tppo.updated_by, tppo.updated_at, versions, is_active, ")
				.append("(SELECT count(*) FROM tb_user_polling_response tupr WHERE polling_option_id = tppo.id) AS total_response ")
				.append("FROM tb_post_polling_option AS tppo ")
				.append("WHERE tppo.post_polling_id = :id");
		final List<?> rows = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("id", id).getResultList();
		rows.forEach(row ->{
			final Object[] rowArr = (Object[]) row;
			final PostPolling postPolling = new PostPolling();
			final PostPollingOption postPollingOption = new PostPollingOption();
			postPollingOption.setId(rowArr[0].toString());
			postPolling.setId(rowArr[1].toString());
			postPollingOption.setPostPolling(postPolling);
			postPollingOption.setContent(rowArr[2].toString());
			postPollingOption.setCreatedBy(rowArr[3].toString());
			postPollingOption.setCreatedAt(Timestamp.valueOf(rowArr[4].toString()).toLocalDateTime());
			if (rowArr[5] != null) {
				postPollingOption.setUpdatedBy(rowArr[5].toString());
			}

			if (rowArr[6] != null) {
				postPollingOption.setUpdatedAt(Timestamp.valueOf(rowArr[6].toString()).toLocalDateTime());
			}

			postPollingOption.setVersion(Integer.valueOf(rowArr[7].toString()));
			postPollingOption.setIsActive(Boolean.valueOf(rowArr[8].toString()));
			postPollingOption.setTotalResponse(Integer.valueOf(rowArr[9].toString()));
			postPollingOptions.add(postPollingOption);
		});
		return postPollingOptions;
	}

}
