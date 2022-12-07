package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConst;
import com.lawencon.community.constant.ResponseConst;
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
	
	private void valUpdate(final Comment data) {
		valIdNotNull(data);
		valIdExist(data);
		valNotBK(data);
	}
	
	private void valIdNotNull(final Comment data) {
		if (data.getId() == null) {
			throw new RuntimeException("Primary Key Id is required!");
		}
	}
	
	private void valIdExist(final Comment data) {
		final Comment result = commentDao.getByIdAndDetach(Comment.class, data.getId());
		final Optional<Comment> optional = Optional.ofNullable(result);
		if (optional.isEmpty()) {
			throw new RuntimeException("Primay Key Id Is Not Exist!");
		}
	}
	
	private void valNotBK(final Comment data) {
		if (data.getContent() == null) {
			throw new RuntimeException("Content is required!");
		}
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
	
	public ResponseDto update(final Comment data)  {
		final ResponseDto response = new ResponseDto();
		final Comment result = commentDao.getByIdAndDetach(Comment.class, data.getId());
		final Optional<Comment> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Comment updateOne = optional.get();
			valUpdate(data);
			try {
				begin();
				if(!updateOne.getUser().getId().equals(principalService.getAuthPrincipal())) {
					throw new RuntimeException("You arent the creator! Update failed!");
				}
				updateOne.setContent(data.getContent());
				updateOne.setIsActive(data.getIsActive());
				updateOne = commentDao.saveAndFlush(updateOne);		
				commit();
				response.setMessage(ResponseConst.UPDATED.getResponse());
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
	public ResponseDto deleteById(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Comment result = commentDao.getByIdAndDetach(Comment.class, id);
		final Optional<Comment> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Comment deleteOne = optional.get();
			try {
				begin();
				final String userId = principalService.getAuthPrincipal();
				System.out.println(userId +" "+ deleteOne.getUser().getId()+" " +deleteOne.getPost().getUser().getId());
				System.out.println(!deleteOne.getUser().getId().equals(userId));
				System.out.println(!deleteOne.getPost().getUser().getId().equals(userId));
				if(!deleteOne.getUser().getId().equals(userId) &&
						!deleteOne.getPost().getUser().getId().equals(userId)) {
					throw new RuntimeException("You arent the creator! Delete failed!");
				}
				commentDao.deleteById(Comment.class, deleteOne.getId());		
				commit();
				response.setMessage(ResponseConst.DELETED.getResponse());
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
}
