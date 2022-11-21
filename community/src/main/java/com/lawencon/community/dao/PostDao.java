package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostAttachment;
import com.lawencon.community.model.PostPolling;
import com.lawencon.community.model.PostPollingOption;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.service.UserService;
import com.lawencon.security.principal.PrincipalService;

@Repository
public class PostDao extends AbstractJpaDao {
	@Autowired
	private PrincipalService principalService;

	@Autowired
	private UserService userService;

	@Autowired
	private PostTypeDao postTypeDao;

	@Autowired
	private PostAttachmentDao postAttachmentDao;

	@Autowired
	private PostPollingDao postPollingDao;
	@Autowired
	private PostPollingOptionDao postPollingOptionDao;

	public List<Post> getAll() {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp");

		@SuppressWarnings("unchecked")
		final List<Object[]> rows = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("user_id", principalService.getAuthPrincipal()).getResultList();
		final List<Post> posts = setObjToPost(rows);
		return posts;
	}

	private List<Post> setObjToPost(final List<Object[]> rows) {
		final List<Post> posts = new ArrayList<>();
		for (Object[] row : rows) {
			final Post post = new Post();
			post.setId(row[0].toString());
			post.setTitle(row[1].toString());
			post.setBody(row[2].toString());
			final User user = userService.getById(row[3].toString());
			post.setUser(user);
			final PostType postType = postTypeDao.getById(PostType.class, row[4].toString());
			post.setPostType(postType);
			post.setCreatedBy(row[5].toString());
			post.setCreatedAt(Timestamp.valueOf(row[6].toString()).toLocalDateTime());
			if (row[7] != null) {
				post.setUpdatedBy(row[7].toString());
			}

			if (row[8] != null) {
				post.setUpdatedAt(Timestamp.valueOf(row[7].toString()).toLocalDateTime());
			}

			post.setVersion(Integer.valueOf(row[9].toString()));
			post.setIsActive(Boolean.valueOf(row[10].toString()));
			final Integer totalLike = Integer.valueOf(row[11].toString());
			post.setTotalLike(totalLike);
			post.setTotalComment(Integer.valueOf(row[12].toString()));
			if (row[13] != null) {
				post.setLike_id(row[13].toString());
			}
			if (row[14] != null) {
				post.setBookmark_id(row[14].toString());
			}
			final List<PostAttachment> attachments = postAttachmentDao.getAllByPost(row[0].toString());
			post.setFile(attachments);
			final PostPolling postPolling =  postPollingDao.getByPost(row[0].toString());
			if(postPolling.getId() != null) {
				final List<PostPollingOption> postPollingOptions = postPollingOptionDao.getAllByPostPolling(postPolling.getId());
				postPolling.setPostPollingOptions(postPollingOptions);
				post.setPostPollingOption(postPollingOptions);
				post.setQuestion(postPolling.getQuestion());
				
			}
			posts.add(post);
		}
		return posts;
	}
}
