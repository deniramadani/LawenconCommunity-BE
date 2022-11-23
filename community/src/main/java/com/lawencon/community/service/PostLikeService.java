package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostLikeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostLikeService extends BaseCoreService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private PrincipalService principalService;

	@Autowired
	private PostLikeDao postLikeDao;

	private void valInsert(final PostLike data) {
		valNotNull(data);
		valIdNull(data);
		valFkFound(data);
	}

	private void valNotNull(PostLike data) {
		if (data.getPost().getId() == null) {
			throw new RuntimeException("Id Post is Required.");
		}
	}

	private void valIdNull(PostLike data) {
		if (data.getId() != null) {
			throw new RuntimeException("Id is set, Expected not set.");
		}

	}

	private void valFkFound(PostLike data) {
		final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
		final Post post = postDao.getById(Post.class, data.getPost().getId());
		if (user == null) {
			throw new RuntimeException("User Not Found");
		}
		if (post == null) {
			throw new RuntimeException("Post Not Found");
		}
	}

	public ResponseDto insert(final PostLike data) {
		final ResponseDto responseDto = new ResponseDto();
		final PostLike postLike = new PostLike();
		try {
			valInsert(data);
			begin();
			final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
			postLike.setUser(user);

			final Post post = postDao.getById(Post.class, data.getPost().getId());
			postLike.setPost(post);
			if (!(post.getPostType().getPostTypeCode().equalsIgnoreCase(PostTypeConst.PREMIUM.getPostTypeCodeEnum())
					&& UserTypeConst.PREMIUM.getUserTypeCodeEnum()
							.equalsIgnoreCase(user.getUserType().getUserTypeCode()))) {
				throw new RuntimeException("Premium Access Only!");
			}
			postLikeDao.save(postLike);
			responseDto.setMessage("You Liked This Post");
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return responseDto;
	}

	public ResponseDto delete(String id) {
		final ResponseDto responseDto = new ResponseDto();
		try {
			begin();
			final PostLike postLike = postLikeDao.getById(PostLike.class, id);
			if (postLike == null) {
				throw new RuntimeException("You are not liked this Post yet");
			}
			postLikeDao.deleteById(PostLike.class, id);
			responseDto.setMessage("You Success Canceling like This Post");
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setMessage(e.getMessage());
			rollback();
		}
		return responseDto;
	}
}
