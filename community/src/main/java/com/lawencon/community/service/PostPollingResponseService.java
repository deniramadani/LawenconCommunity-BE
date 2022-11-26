package com.lawencon.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PostPollingOptionDao;
import com.lawencon.community.dao.PostPollingResponseDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.PostPollingOption;
import com.lawencon.community.model.PostPollingResponse;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostPollingResponseService extends BaseCoreService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private PostPollingOptionDao postPollingOptionDao;

	@Autowired
	private PrincipalService principalService;

	@Autowired
	private PostPollingResponseDao postPollingResponseDao;

	private void valInsert(final PostPollingResponse data) {
		valNotNull(data);
		valIdNull(data);
		valFkFound(data);
	}

	private void valNotNull(PostPollingResponse data) {
		if (data.getPostPollingOption().getId() == null) {
			throw new RuntimeException("Id Post is Required.");
		}
	}

	private void valIdNull(PostPollingResponse data) {
		if (data.getId() != null) {
			throw new RuntimeException("Id is set, Expected not set.");
		}

	}

	private void valFkFound(PostPollingResponse data) {
		final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
		final PostPollingOption postPollingOption = postPollingOptionDao.getById(PostPollingOption.class, data.getPostPollingOption().getId());
		if (user == null) {
			throw new RuntimeException("User Not Found");
		}
		if (postPollingOption == null) {
			throw new RuntimeException("Option Not Found");
		}
	}

	public ResponseDto insert(final PostPollingResponse data) {
		final ResponseDto responseDto = new ResponseDto();
		final PostPollingResponse postPollingResponse = new PostPollingResponse();
		try {
			valInsert(data);
			begin();
			final User user = userDao.getById(User.class, principalService.getAuthPrincipal());
			postPollingResponse.setUser(user);

			final PostPollingOption postPollingOption = postPollingOptionDao.getById(PostPollingOption.class, data.getPostPollingOption().getId());
			postPollingResponse.setPostPollingOption(postPollingOption);
			
			postPollingResponseDao.save(postPollingResponse);
			responseDto.setMessage("Your Response Saved");
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return responseDto;
	}

	
}
