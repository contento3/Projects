package com.contento3.cms.site.structure.domain.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.site.structure.domain.dto.SiteDomainDto;
import com.contento3.cms.site.structure.domain.model.SiteDomain;
import com.contento3.cms.site.structure.domain.service.SiteDomainAssembler;

public class SiteDomainAssemblerImpl implements SiteDomainAssembler {

	@Override
	public SiteDomain dtoToDomain(final SiteDomainDto dto) {
		final SiteDomain domain = new SiteDomain();
		domain.setDomainId(dto.getDomainId());
		domain.setDomainName(dto.getDomainName());
		return domain;
	}

	@Override
	public SiteDomainDto domainToDto(final SiteDomain domain) {
		SiteDomainDto dto= new SiteDomainDto();
		dto.setDomainId(domain.getDomainId());
		dto.setDomainName(domain.getDomainName());
		return dto;
	}

	@Override
	public Collection<SiteDomainDto> domainsToDtos(final Collection<SiteDomain> domains) {
		Collection<SiteDomainDto> dtos = new ArrayList<SiteDomainDto>();
		for (SiteDomain siteDomain : domains) {
			dtos.add(domainToDto(siteDomain));
		}
		return dtos;
	}

	@Override
	public Collection<SiteDomain> dtosToDomains(final Collection<SiteDomainDto> dtos) {
		Collection<SiteDomain> domains = new ArrayList<SiteDomain>();
		for (SiteDomainDto siteDomainDto : dtos) {
			domains.add(dtoToDomain(siteDomainDto));
		}
		return domains;
	}

}
