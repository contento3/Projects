package com.contento3.cms.site.structure.domain.service.impl;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.site.structure.domain.dao.SiteDomainDao;
import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.model.SiteDomain;
import com.contento3.cms.site.structure.domain.service.SiteDomainAssembler;
import com.contento3.cms.site.structure.domain.service.SiteDomainService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class SiteDomainServiceImpl implements SiteDomainService {

	private SiteDomainDao siteDomainDao;
	private SiteDomainAssembler siteDomainAssembler;
	
	public SiteDomainServiceImpl(final SiteDomainAssembler siteDomainAssembler,final SiteDomainDao siteDomainDao) {
		Validate.notNull(siteDomainAssembler,"siteDomainAssembler cannot be null");
		Validate.notNull(siteDomainDao,"siteDomainDao cannot be null");
		this.siteDomainAssembler= siteDomainAssembler;
		this.siteDomainDao= siteDomainDao;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(final SiteDomainDto dto) {
		Validate.notNull(dto,"dto cannot be null");
		return siteDomainDao.persist(siteDomainAssembler.dtoToDomain(dto));
	}
	
	@Transactional(readOnly = false)
	@Override
	public void update(final SiteDomainDto dto){
		Validate.notNull(dto ,"dto cannot be null");
		siteDomainDao.update(siteDomainAssembler.dtoToDomain(dto));
	}
	@Transactional(readOnly = false)
	@Override
	public void delete(final SiteDomainDto dto){
		Validate.notNull(dto ,"dto cannot be null");
		siteDomainDao.delete(siteDomainAssembler.dtoToDomain(dto));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDomainDto findSiteDomainByName(final String domainName) {
		Validate.notNull(domainName ,"domainName cannot be null");
		SiteDomain siteDomain = siteDomainDao.findSiteDomainByName(domainName);
		return siteDomainAssembler.domainToDto(siteDomain);
	}

	@Override
	public SiteDomainDto findById(Integer siteDomainId) {
		Validate.notNull(siteDomainId ,"siteDomainId cannot be null");
		SiteDomain siteDomain = siteDomainDao.findById(siteDomainId);
		return siteDomainAssembler.domainToDto(siteDomain);
	}


}
