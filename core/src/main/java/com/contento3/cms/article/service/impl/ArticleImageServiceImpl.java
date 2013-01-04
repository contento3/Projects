package com.contento3.cms.article.service.impl;

import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;

public class ArticleImageServiceImpl implements ArticleImageService {

	public ArticleImageServiceImpl() {
	
	}
	
	@Override
	public Object create(final ArticleImageDto dto)
			throws EntityAlreadyFoundException {
	
		return null;
	}

	@Override
	public void delete(final ArticleImageDto dtoToDelete)
			throws EntityCannotBeDeletedException {
	

	}

}
