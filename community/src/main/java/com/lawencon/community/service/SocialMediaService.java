package com.lawencon.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.model.SocialMedia;

@Service
public class SocialMediaService extends BaseCoreService {
	@Autowired
	private SocialMediaDao socialMediaDao;

	public List<SocialMedia> getAll() {
		return socialMediaDao.getAll(SocialMedia.class);
	}
}
