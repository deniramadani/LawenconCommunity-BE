package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.CommentDao;
import com.lawencon.community.model.Comment;

@Service
public class CommentService extends BaseCoreService {
	@Autowired
	private CommentDao commentDao;

	public List<Comment> getAllByPost(String id) {
		return commentDao.getByPost(id);
	}
}
