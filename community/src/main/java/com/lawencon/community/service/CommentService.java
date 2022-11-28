package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConst;
import com.lawencon.community.constant.UserTypeConst;
import com.lawencon.community.dao.CommentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Comment;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CommentService extends BaseCoreService {
	
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private PostDao postDao;
	
	public List<Comment> getAllByPost(final String id) {
		return commentDao.getByPost(id);
	}
	
	public ResponseDto insert(final Comment data) {
		final ResponseDto responseDto = new ResponseDto();
		final Comment comment = new Comment();
		try {
			valInsert(data);
			begin();
			comment.setContent(data.getContent());
			final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
			comment.setUser(user);
			
			final Post post = postDao.getById(Post.class, data.getPost().getId());
			comment.setPost(post);
			if(post.getPostType().getPostTypeCode().equalsIgnoreCase(PostTypeConst.PREMIUM.getPostTypeCodeEnum()) && !UserTypeConst.PREMIUM.getUserTypeCodeEnum().equalsIgnoreCase(user.getUserType().getUserTypeCode())){
				throw new RuntimeException("Premium Access Only!");
			}
			commentDao.save(comment);
			responseDto.setMessage("Your Comment is Published");
			commit();			
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setMessage(e.getMessage());
			rollback();
		}
		return responseDto;
	}
	
	private void valInsert(final Comment data) {
		valNotNull(data);
		valIdNull(data);
		valFkFound(data);
	}

	private void valFkFound(final Comment data) {
		final Post post = postDao.getById(Post.class, data.getPost().getId());
		if(post == null) {
			throw new RuntimeException("Entity Post Not Found!");
		}
		final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
		if(user == null) {
			throw new RuntimeException("Entity User Not Found!");
		}
	}

	private void valIdNull(final Comment data) {
		if (data.getId() != null) { 
			throw new RuntimeException("Id Is Set. Expected Not Set");
		}
	}

	private void valNotNull(final Comment data) {
		if(data.getPost() == null) {
			if(data.getPost().getId() == null) {
				throw new RuntimeException("Id Post Required.");
			}
			throw new RuntimeException("Post Required.");
		}
		if(data.getContent() == null) {
			throw new RuntimeException("Content Required.");
		}
	}
	
}
