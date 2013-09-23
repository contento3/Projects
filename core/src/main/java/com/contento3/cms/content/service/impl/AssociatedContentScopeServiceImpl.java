package com.contento3.cms.content.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.content.dao.AssociatedContentScopeDao;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.model.AssociatedContentScopeTypeEnum;
import com.contento3.cms.content.service.AssociatedContentScopeAssembler;
import com.contento3.cms.content.service.AssociatedContentScopeService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;

@Transactional(readOnly = false , propagation = Propagation.REQUIRES_NEW)
public class AssociatedContentScopeServiceImpl implements AssociatedContentScopeService {

	/**
	 * Assembler for conversion
	 */
	private AssociatedContentScopeAssembler associatedContentScopeAssembler;
	
	/**
	 * AssociatedContentScopeDao for Dao related activity
	 */
	private AssociatedContentScopeDao associatedContentScopeDao;
	
	public AssociatedContentScopeServiceImpl(final AssociatedContentScopeAssembler associatedContentScopeAssembler,
			final AssociatedContentScopeDao associatedContentScopeDao) {
		Validate.notNull(associatedContentScopeAssembler,"associatedContentScopeAssembler cannot be null");
		Validate.notNull(associatedContentScopeDao,"associatedContentScopeDao cannot be null");
		this.associatedContentScopeAssembler = associatedContentScopeAssembler;
		this.associatedContentScopeDao = associatedContentScopeDao;
	}
	
	@Override
	public Object create(final AssociatedContentScopeDto dto)
			throws EntityAlreadyFoundException {
		Validate.notNull(dto,"dto cannot be null");
		return associatedContentScopeDao.persist(associatedContentScopeAssembler.dtoToDomain(dto));
	}

	@Override
	public void delete(final AssociatedContentScopeDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		associatedContentScopeDao.delete(associatedContentScopeAssembler.dtoToDomain(dtoToDelete));

	}

	@Override
	public 	Collection<AssociatedContentScopeDto> getContentScopeForType(final AssociatedContentScopeTypeEnum scopeType)
	{
		Validate.notNull(scopeType,"scopeType cannot be null");
		return this.associatedContentScopeAssembler.domainsToDtos(this.associatedContentScopeDao.findAll());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	@Override
	public AssociatedContentScopeDto findById(Integer contentScopeId) {
		Validate.notNull(contentScopeId,"contentScopeId cannot be null");
		return this.associatedContentScopeAssembler.domainToDto(this.associatedContentScopeDao.findById(contentScopeId));
	}

}
