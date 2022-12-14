package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Comment;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostAttachment;
import com.lawencon.community.model.PostPolling;
import com.lawencon.community.model.PostPollingOption;
import com.lawencon.community.model.PostPollingResponse;
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
	private PostPollingResponseDao postPollingResponseDao;
	@Autowired
	private PostPollingOptionDao postPollingOptionDao;
	@Autowired
	private CommentDao commentDao;

	public List<Post> getAll() {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp WHERE is_active = true ORDER BY tp.created_at DESC");

		@SuppressWarnings("unchecked")
		final List<Object[]> rows = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("user_id", principalService.getAuthPrincipal()).getResultList();
		final List<Post> posts = setObjToPost(rows);
		return posts;
	}

	public List<Post> getAll(final Integer start, final Integer limit) {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp WHERE tp.is_active = true ORDER BY tp.created_at DESC LIMIT :limit OFFSET :start");
		@SuppressWarnings("unchecked")
		final List<Object[]> rows = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("user_id", principalService.getAuthPrincipal()).setParameter("limit", limit)
				.setParameter("start", start).getResultList();
		final List<Post> posts = setObjToPost(rows);
		return posts;
	}
	
	public List<Post> getAllByUserLike(final Integer start, final Integer limit) {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp WHERE tp.id IN (SELECT tpl3.post_id FROM tb_post_like tpl3 WHERE tpl3.user_id = :user_id AND tpl3.is_active = true) AND is_active = true ORDER BY tp.created_at ASC LIMIT :limit OFFSET :start");
		@SuppressWarnings("unchecked")
		final List<Object[]> rows = ConnHandler.getManager().createNativeQuery(query.toString())
			.setParameter("user_id", principalService.getAuthPrincipal()).setParameter("limit", limit)
			.setParameter("start", start).getResultList();
		final List<Post> posts = setObjToPost(rows);
		return posts;
	}
	
	public List<Post> getAllByUserBookmark(final Integer start, final Integer limit) {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp WHERE tp.id IN (SELECT tpb2.post_id FROM tb_post_bookmark tpb2  WHERE tpb2.user_id = :user_id AND tpb2.is_active = true) AND is_active = true ORDER BY tp.created_at DESC LIMIT :limit OFFSET :start");
		@SuppressWarnings("unchecked")
		final List<Object[]> rows = ConnHandler.getManager().createNativeQuery(query.toString())
			.setParameter("user_id", principalService.getAuthPrincipal()).setParameter("limit", limit)
			.setParameter("start", start).getResultList();
		final List<Post> posts = setObjToPost(rows);
		return posts;
	}

	public Post getById(final String id) {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp WHERE tp.id = :id AND is_active = true ");
		final Object obj = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("user_id", principalService.getAuthPrincipal()).setParameter("id", id).getSingleResult();
		final Post post = setObjByPost(obj);
		return post;
	}

	private Post setObjByPost(final Object obj) {
		final Post post = new Post();
		final Object[] row = (Object[]) obj;
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
			post.setUpdatedAt(Timestamp.valueOf(row[8].toString()).toLocalDateTime());
		}
		post.setVersion(Integer.valueOf(row[9].toString()));
		post.setIsActive(Boolean.valueOf(row[10].toString()));
		final Integer totalLike = Integer.valueOf(row[11].toString());
		post.setTotalLike(totalLike);
		post.setTotalComment(Integer.valueOf(row[12].toString()));
		if (row[13] != null) {
			post.setLikeId(row[13].toString());
		}
		if (row[14] != null) {
			post.setBookmarkId(row[14].toString());
		}
		final List<PostAttachment> attachments = postAttachmentDao.getAllByPost(row[0].toString());
		post.setFile(attachments);
		final PostPolling postPolling = postPollingDao.getByPost(row[0].toString());
		if (postPolling.getId() != null) {
			final List<PostPollingOption> postPollingOptions = postPollingOptionDao
					.getAllByPostPolling(postPolling.getId());
			postPolling.setPostPollingOptions(postPollingOptions);
			Long totalPolling = 0L;
			for(int i= 0; i<postPollingOptions.size();i++ ) {
				
				final String whereClause = "WHERE postPollingOption.id = :ppid";
				final String[] paramName =  {"ppid"};
				final String[] paramValue =  { postPollingOptions.get(i).getPostPolling().getId()};
		
				final Long totalVoted = postPollingResponseDao.countAll(PostPollingResponse.class, whereClause, paramName, paramValue);
				totalPolling += postPollingOptions.get(i).getTotalResponse();;
				if(totalVoted>0) {
					post.setOptionId(postPollingOptions.get(i).getId());
				}
			}
			post.setTotalPolling(totalPolling);
			post.setPostPollingOption(postPollingOptions);
			post.setQuestion(postPolling.getQuestion());
		}
		final List<Comment> comments = commentDao.getByPost(row[0].toString());
		post.setComments(comments);
		return post;
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
				post.setUpdatedAt(Timestamp.valueOf(row[8].toString()).toLocalDateTime());
			}
			post.setVersion(Integer.valueOf(row[9].toString()));
			post.setIsActive(Boolean.valueOf(row[10].toString()));
			final Integer totalLike = Integer.valueOf(row[11].toString());
			post.setTotalLike(totalLike);
			post.setTotalComment(Integer.valueOf(row[12].toString()));
			if (row[13] != null) {
				post.setLikeId(row[13].toString());
			}
			if (row[14] != null) {
				post.setBookmarkId(row[14].toString());
			}
			final List<PostAttachment> attachments = postAttachmentDao.getAllByPost(row[0].toString());
			post.setFile(attachments);
			final PostPolling postPolling = postPollingDao.getByPost(row[0].toString());
			if (postPolling.getId() != null) {
				final List<PostPollingOption> postPollingOptions = postPollingOptionDao
						.getAllByPostPolling(postPolling.getId());
				Long totalPolling = 0L;
				for(int i= 0; i<postPollingOptions.size();i++ ) {
					final String whereClause = "WHERE postPollingOption.id = :ppid AND user.id = :userId";
					final String[] paramName =  {"ppid", "userId"};
					final String[] paramValue =  { postPollingOptions.get(i).getId(), principalService.getAuthPrincipal()};
					final Long totalVoted = postPollingResponseDao.countAll(PostPollingResponse.class, whereClause, paramName, paramValue);
					totalPolling += postPollingOptions.get(i).getTotalResponse();
					if(totalVoted>0) {
						post.setOptionId(postPollingOptions.get(i).getId());
					}
				}
				post.setTotalPolling(totalPolling);
				postPolling.setPostPollingOptions(postPollingOptions);
				post.setPostPollingOption(postPollingOptions);
				post.setQuestion(postPolling.getQuestion());
			}
			posts.add(post);
		}
		return posts;
	}
	
	public List<Post> getAllByUserOrOwner(final String user_id, final Integer start, final Integer limit) {
		final StringBuilder query = new StringBuilder().append(
				"SELECT tp.id, tp.title, tp.body ,tp.user_id , tp.type_post_id , tp.created_by , tp.created_at , tp.updated_by , tp.updated_at , tp.versions , tp.is_active , ")
				.append("(SELECT COUNT(tc.id) FROM tb_post_like tc WHERE tc.post_id = tp.id AND is_active = true) AS total_like, ")
				.append("(SELECT COUNT(tpl.id) FROM tb_comment tpl WHERE tpl.post_id = tp.id AND is_active = true) AS total_comment, ")
				.append("(SELECT tpl2.id FROM tb_post_like tpl2 WHERE tpl2.post_id =tp.id AND tpl2.user_id = :user_id AND is_active = true) AS like_id, ")
				.append("(SELECT tpb.id FROM tb_post_bookmark tpb WHERE tpb.post_id =tp.id AND tpb.user_id = :user_id AND is_active = true) AS bookmark_id ")
				.append("FROM tb_post tp WHERE tp.user_id = :user_id AND tp.is_active = true ORDER BY tp.created_at DESC LIMIT :limit OFFSET :start");
		@SuppressWarnings("unchecked")
		final List<Object[]> rows = ConnHandler.getManager().createNativeQuery(query.toString())
				.setParameter("user_id", user_id).setParameter("limit", limit)
				.setParameter("start", start).getResultList();
		final List<Post> posts = setObjToPost(rows);
		return posts;
	}
	
}
