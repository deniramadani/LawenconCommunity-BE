package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.PostBookmarkDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostBookmark;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostBookmarkService extends BaseCoreService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private PostBookmarkDao postBookmarkDao;
	
	private void valInsert(final PostBookmark data){
		valNotNull(data);
		valIdNull(data);
	}

	private void valNotNull(final PostBookmark data) {
		if (data.getPost().getId() == null) {
			throw new RuntimeException("Post Id Required.");
		}
	}
	
	private void valIdNull(final PostBookmark data) {
		if (data.getId() != null) { 
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
	}
	
	public ResponseDto insert(final PostBookmark data) {
		final ResponseDto responseDto = new ResponseDto();
		final PostBookmark postBookmark = new PostBookmark();
		try {
			valInsert(data);
			begin();
			final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
			postBookmark.setUser(user);

			final Post post = postDao.getById(Post.class, data.getPost().getId());
			postBookmark.setPost(post);
			if (!(post.getPostType().getPostTypeCode().equalsIgnoreCase(PostTypeConst.PREMIUM.getPostTypeCodeEnum())
					&& UserTypeConst.PREMIUM.getUserTypeCodeEnum()
							.equalsIgnoreCase(user.getUserType().getUserTypeCode()))) {
				throw new RuntimeException("Premium Access Only!");
			}
			postBookmarkDao.save(postBookmark);
			responseDto.setMessage("You Bookmarked This Post");
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setMessage("Your Action For Bookmark is Failed");
			rollback();
		}
		return responseDto;
	}

	public ResponseDto delete(final String id) {
		final ResponseDto responseDto = new ResponseDto();
		try {
			begin();
			postBookmarkDao.deleteById(PostLike.class, id);
			responseDto.setMessage("You Success Canceling Bookmark This Post");
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setMessage(e.getMessage());
			rollback();
		}
		return responseDto;
	}

}
