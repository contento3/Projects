package com.contento3.cms.site.structure.domain.service.impl;

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
		this.siteDomainAssembler= siteDomainAssembler;
		this.siteDomainDao= siteDomainDao;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(SiteDomainDto dto) {
		siteDomainDao.persist(siteDomainAssembler.dtoToDomain(dto));
	}
	
	@Transactional(readOnly = false)
	@Override
	public void update(SiteDomainDto dto){
		siteDomainDao.update(siteDomainAssembler.dtoToDomain(dto));
	}
	@Transactional(readOnly = false)
	@Override
	public void delete(SiteDomainDto dto){
		siteDomainDao.delete(siteDomainAssembler.dtoToDomain(dto));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public SiteDomainDto findSiteDomainByName(final String domainName) {
		SiteDomain siteDomain = siteDomainDao.findSiteDomainByName(domainName);
		return siteDomainAssembler.domainToDto(siteDomain);
	}


}
