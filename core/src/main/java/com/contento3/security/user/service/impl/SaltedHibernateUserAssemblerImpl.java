package com.contento3.security.user.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;

public class SaltedHibernateUserAssemblerImpl implements SaltedHibernateUserAssembler {

	@Override
	public SaltedHibernateUser dtoToDomain(final SaltedHibernateUserDto dto) {
		SaltedHibernateUser domain = new SaltedHibernateUser();
		domain.setUserName(dto.getUserName());
		return domain;
	}

	@Override
	public SaltedHibernateUserDto domainToDto(final SaltedHibernateUser domain) {
		SaltedHibernateUserDto dto = new SaltedHibernateUserDto();
		dto.setUserName(domain.getUserName());
		return dto;
	}

	@Override
	public Collection<SaltedHibernateUserDto> domainsToDtos(
			final Collection<SaltedHibernateUser> domains) {
		Collection<SaltedHibernateUserDto> dtos = new ArrayList<SaltedHibernateUserDto>();
		for(SaltedHibernateUser domain:domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<SaltedHibernateUser> dtosToDomains(
			final Collection<SaltedHibernateUserDto> dtos) {
		Collection<SaltedHibernateUser> domains = new ArrayList<SaltedHibernateUser>();
		for(SaltedHibernateUserDto dto:dtos){
			domains.add(dtoToDomain(dto));
		}
		
		return domains;
	}

}
