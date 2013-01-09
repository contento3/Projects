package com.contento3.security.user.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.service.AccountAssembler;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;

public class SaltedHibernateUserAssemblerImpl implements SaltedHibernateUserAssembler {

	/**
	 * Transform Account to AccountDto and vice versa.
	 */
	private AccountAssembler accountAssembler;
	public SaltedHibernateUserAssemblerImpl(final AccountAssembler accountAssembler) {
		this.accountAssembler = accountAssembler;
	}
	
	@Override
	public SaltedHibernateUser dtoToDomain(final SaltedHibernateUserDto dto) {
		SaltedHibernateUser domain = new SaltedHibernateUser();
		domain.setUserName(dto.getName());
		domain.setEnabled(dto.isEnabled());
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
		return domain;
	}

	@Override
	public SaltedHibernateUserDto domainToDto(final SaltedHibernateUser domain) {
		SaltedHibernateUserDto dto = new SaltedHibernateUserDto();
		dto.setUserName(domain.getUserName());
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
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
