package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ArticleService extends BaseCoreService {
	
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService principalService;
	
	public ResponseDto insert(final Article data)  {
		final ResponseDto response = new ResponseDto();
		Article insertOne = new Article();
		try {
			begin();
			insertOne.setTitle(data.getTitle());
			insertOne.setContent(data.getContent());
			if(data.getFile()!=null) {
				File file = new File();
				file.setFileEncode(data.getFile().getFileEncode());
				file.setFileExtensions(data.getFile().getFileExtensions());
				file = fileDao.save(file);
				insertOne.setFile(file);
			}
			insertOne = articleDao.save(insertOne);			
			commit();
			response.setMessage(ResponseConst.CREATED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}
	
	public ResponseDto update(final Article data)  {
		final ResponseDto response = new ResponseDto();
		final Article result = articleDao.getByIdAndDetach(Article.class, data.getId());
		final Optional<Article> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Article updateOne = optional.get();
			try {
				begin();
				updateOne.setTitle(data.getTitle());
				updateOne.setContent(data.getContent());
				if (data.getFile() != null) {
					File file = new File();
					file.setFileEncode(data.getFile().getFileEncode());
					file.setFileExtensions(data.getFile().getFileExtensions());
					file = fileDao.save(file);				
					updateOne.setFile(file);
				}
				updateOne.setIsActive(data.getIsActive());
				updateOne = articleDao.saveAndFlush(updateOne);		
				commit();
				response.setMessage(ResponseConst.UPDATED.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
	public Article getById(final String id) {
		final Article result = articleDao.getByIdAndDetach(Article.class, id);
		final Optional<Article> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			final Article findOne = optional.get();
			return findOne;			
		} else {
			throw new RuntimeException("Article not found!");	
		}
	}
	
	public List<Article> getAll(final Integer start, final Integer limit) {
		return articleDao.getAllArticle(start, limit) ;
	}
	
	public List<Article> getAllByUserId(final Integer start, final Integer limit) {
		return articleDao.getAllByUserId(start, limit, principalService.getAuthPrincipal()) ;
	}
	
	public ResponseDto deleteById(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Article result = articleDao.getByIdAndDetach(Article.class, id);
		final Optional<Article> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Article updateOne = optional.get();
			try {
				begin();
				updateOne.setIsActive(false);
				updateOne = articleDao.saveAndFlush(updateOne);		
				commit();
				response.setMessage(ResponseConst.DELETED.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
}
