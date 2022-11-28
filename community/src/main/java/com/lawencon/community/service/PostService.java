package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConst;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PostAttachmentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostPollingDao;
import com.lawencon.community.dao.PostPollingOptionDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostAttachment;
import com.lawencon.community.model.PostPolling;
import com.lawencon.community.model.PostPollingOption;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostService extends BaseCoreService {

	@Autowired
	private PostDao postDao;
	@Autowired
	private PostAttachmentDao postAttachmentDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PostTypeDao postTypeDao;
	@Autowired
	private PostPollingDao postPollingDao;
	@Autowired
	private PostPollingOptionDao postPollingOptionDao;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private UserDao userDao;

	public ResponseDto insertBasic(final Post data) {
		return insert(data, PostTypeConst.BASIC.getPostTypeCodeEnum());
	}

	public ResponseDto insertPremium(final Post data) {
		final User user = userDao.getByIdAndDetach(User.class, principalService.getAuthPrincipal());
		if (user.getUserType().getUserTypeCode().equalsIgnoreCase(UserTypeConst.PREMIUM.getUserTypeCodeEnum())) {
			return insert(data, PostTypeConst.PREMIUM.getPostTypeCodeEnum());
		} else {
			throw new RuntimeException("Premium access only!");
		}
	}

	public ResponseDto insertPolling(final Post data) {
		return insert(data, PostTypeConst.POLLING.getPostTypeCodeEnum());
	}

	private void valInsert(final Post data, final String postTypeCode) {
		valNotNull(data);
		valIdNull(data);
		valFkFound(data, postTypeCode);
	}

	private void valFkFound(Post data, final String postTypeCode) {
		final Optional<PostType> postType = postTypeDao.getByCode(postTypeCode);
		if (!postType.isPresent()) {
			throw new RuntimeException("Post Type Not Found.");
		}
	}

	private void valIdNull(Post data) {
		if (data.getId() != null) {
			throw new RuntimeException("Id is Set. Expected Not Set");
		}
	}

	private void valIdNotNull(Post data) {
		if (data.getId() == null) {
			throw new RuntimeException("Id is Not Set. Expected Not Set");
		}
	}

	private void valNotNull(Post data) {
		if (data.getTitle() == null) {
			throw new RuntimeException("Title is Required");
		}
		if (data.getBody() == null) {
			throw new RuntimeException("Body is Required");
		}
	}

	private void valUpdate(final Post data) {
		valIdNotNull(data);
	}

	public ResponseDto update(final Post data) {
		final ResponseDto response = new ResponseDto();
		valUpdate(data);
		final Post row = postDao.getByIdAndDetach(Post.class, data.getId());
		try {
			begin();
			row.setTitle(data.getTitle());
			row.setBody(data.getBody());
			postDao.saveAndFlush(row);
			commit();
			response.setMessage(ResponseConst.UPDATED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}

	public ResponseDto delete(final String id) {
		final ResponseDto response = new ResponseDto();
		final Post row = postDao.getByIdAndDetach(Post.class, id);
		try {
			begin();
			row.setIsActive(!row.getIsActive());
			postDao.saveAndFlush(row);
			commit();
			response.setMessage(ResponseConst.DELETED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}

	private ResponseDto insert(final Post data, final String postTypeCode) {
		final ResponseDto response = new ResponseDto();
		Post row = new Post();
		valInsert(data, postTypeCode);
		try {
			begin();
			row.setTitle(data.getTitle());
			row.setBody(data.getBody());
			// Foreign Key
			final User user = userDao.getByIdAndDetach(User.class, principalService.getAuthPrincipal());
			row.setUser(user);
			final Optional<PostType> postType = postTypeDao.getByCode(postTypeCode);
			row.setPostType(postType.get());
			// Foreign Key
			row = postDao.save(row);
			if (!postTypeCode.equalsIgnoreCase(PostTypeConst.POLLING.getPostTypeCodeEnum())) {
				if (data.getPfile() != null && data.getPfile().size() >= 0) {
					for (int i = 0; i < data.getPfile().size(); i++) {
						File file = new File();
						file.setFileEncode(data.getPfile().get(i).getFileEncode());
						file.setFileExtensions(data.getPfile().get(i).getFileExtensions());
						file = fileDao.save(file);
						PostAttachment bridgeFile = new PostAttachment();
						bridgeFile.setFile(file);
						bridgeFile.setPost(row);
						bridgeFile = postAttachmentDao.save(bridgeFile);
					}
				}
			} else {
				if (data.getPostPollingOption() != null && data.getPostPollingOption().size() >= 0) {
					PostPolling polling = new PostPolling();
					polling.setPost(row);
					polling.setQuestion(data.getQuestion());
					polling = postPollingDao.save(polling);
					for (int i = 0; i < data.getPostPollingOption().size(); i++) {
						PostPollingOption pollingOption = new PostPollingOption();
						pollingOption.setPostPolling(polling);
						pollingOption.setContent(data.getPostPollingOption().get(i).getContent());
						pollingOption = postPollingOptionDao.save(pollingOption);
					}
				}
			}
			commit();
			response.setMessage(ResponseConst.CREATED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}

	public List<Post> getAll(final int start, final int limit) {
		final List<Post> posts = postDao.getAll(start, limit);
		return posts;
	}

	public List<Post> getAllByLike(final int start, final int limit) {
		final List<Post> posts = postDao.getAllByUserLike(start, limit);
		return posts;
	}

	public List<Post> getAllByBookmark(final int start, final int limit) {
		final List<Post> posts = postDao.getAllByUserBookmark(start, limit);
		return posts;
	}

	public List<Post> getAll() {
		final List<Post> posts = postDao.getAll();
		return posts;
	}

	public Post getById(String id) {
		final Post post = postDao.getById(id);
		return post;
	}

}
