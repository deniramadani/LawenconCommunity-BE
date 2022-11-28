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

	public ResponseDto insert(final PostLike data) {
		final ResponseDto responseDto = new ResponseDto();
		final PostLike postLike = new PostLike();
		try {
//			valInsert(data);
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
			postBookmarkDao.save(postLike);
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
