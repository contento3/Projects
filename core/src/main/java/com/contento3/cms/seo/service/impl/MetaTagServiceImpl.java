package com.contento3.cms.seo.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.seo.dao.MetaTagDAO;
import com.contento3.cms.seo.dto.MetaTagDto;
import com.contento3.cms.seo.service.MetaTagAssembler;
import com.contento3.cms.seo.service.MetaTagService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class MetaTagServiceImpl implements MetaTagService {

	private MetaTagDAO metaTagDao;
	private MetaTagAssembler metaTagAssembler;
	
	public MetaTagServiceImpl(final MetaTagAssembler assembler, final MetaTagDAO dao) {
		
		this.metaTagAssembler = assembler;
		this.metaTagDao = dao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
    public Integer create(final MetaTagDto metaTagDto)  {

    	return metaTagDao.persist(metaTagAssembler.dtoToDomain(metaTagDto));
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final MetaTagDto metaTagDto) {
		
		metaTagDao.update(metaTagAssembler.dtoToDomain(metaTagDto));
	}
}
