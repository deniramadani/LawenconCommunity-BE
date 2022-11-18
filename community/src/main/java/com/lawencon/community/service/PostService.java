package com.lawencon.community.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConst;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PostAttachmentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostPollingDao;
import com.lawencon.community.dao.PostPollingOptionDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostAttachment;
import com.lawencon.community.model.PostPolling;
import com.lawencon.community.model.PostPollingOption;
import com.lawencon.community.model.PostType;

@Service
public class PostService extends BaseCoreService{

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
	PostPollingOptionDao postPollingOptionDao;
	
	public ResponseDto insertBasic(final Post data) {
		return insert(data, PostTypeConst.BASIC.getPostTypeCodeEnum());
	}
	
	public ResponseDto insertPremium(final Post data) {
		return insert(data, PostTypeConst.PREMIUM.getPostTypeCodeEnum());
	}
	
	public ResponseDto insertPolling(final Post data) {
		return insert(data, PostTypeConst.POLLING.getPostTypeCodeEnum());
	}
	
	private ResponseDto insert(final Post data, final String postTypeCode)  {
		final ResponseDto response = new ResponseDto();
		Post row = new Post();
		try {
			begin();
			row.setTitle(data.getTitle());
			row.setBody(data.getBody());
				//Foreign Key
			row.setUser(data.getUser());
			final Optional<PostType> postType = postTypeDao.getByCode(postTypeCode);
			row.setPostType(postType.get());
				//Foreign Key
			row = postDao.save(row);
			if(!postTypeCode.equalsIgnoreCase(PostTypeConst.POLLING.getPostTypeCodeEnum())) {
				if(data.getFile() != null && data.getFile().size() >=0) {
					for(int i=0; i<data.getFile().size(); i++) {
						File file = new File();
						file.setFileEncode(data.getFile().get(i).getFileEncode());
						file.setFileExtensions(data.getFile().get(i).getFileExtensions());
						file = fileDao.save(file);
						PostAttachment bridgeFile = new PostAttachment();
						bridgeFile.setFile(file);
						bridgeFile.setPost(row);
						bridgeFile = postAttachmentDao.save(bridgeFile);
					}				
				}
			} else {
				if(data.getPostPollingOption() != null && data.getPostPollingOption().size() >= 0) {
					PostPolling polling = new PostPolling();
					polling.setPost(row);
					polling.setQuestion(data.getQuestion());
					polling = postPollingDao.save(polling);
					for(int i=0; i<data.getPostPollingOption().size(); i++) {
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
	
}
