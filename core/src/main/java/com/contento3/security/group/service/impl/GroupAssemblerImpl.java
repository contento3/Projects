package com.contento3.security.group.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.service.AccountAssembler;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;
import com.contento3.security.group.service.GroupAssembler;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;


public class GroupAssemblerImpl implements GroupAssembler {

	/**
	 * SaltedHibernateUserAssembler
	 */
	private SaltedHibernateUserAssembler saltedHibernateUserAssembler ;
	
	/**
	 * AccountAssembler
	 */
	private AccountAssembler accountAssembler;
	
	public GroupAssemblerImpl(final SaltedHibernateUserAssembler saltedHibernateUserAssembler,final AccountAssembler accountAssembler) {
		this.saltedHibernateUserAssembler = saltedHibernateUserAssembler;
		this.accountAssembler = accountAssembler;
	}
	
	public Group dtoToDomain(final GroupDto dto){
		Group group = new Group();
		group.setGroupId(dto.getGroupId());
		group.setGroupName(dto.getGroupName());
		group.setDescription(dto.getDescription());
		group.setRoles(dto.getRoles());
		group.setMembers(saltedHibernateUserAssembler.dtosToDomains(dto.getMembers()));
		group.setAccount(accountAssembler.dtoToDomain(dto.getAccountDto()));
		return group;
	}

	public Group dtoToDomain(final GroupDto dto,final Group domain){
		domain.setGroupId(dto.getGroupId());
		domain.setGroupName(dto.getGroupName());
		domain.setDescription(dto.getDescription());
		domain.setRoles(dto.getRoles());
		domain.setMembers(saltedHibernateUserAssembler.dtosToDomains(dto.getMembers()));
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccountDto()));
		return domain;
	}

	public GroupDto domainToDto(final Group domain){
		GroupDto dto = new GroupDto();
		dto.setGroupId(domain.getGroupId());
		dto.setGroupName(domain.getGroupName());
		dto.setDescription(domain.getDescription());
		dto.setRoles(domain.getRoles());
		dto.setMembers(saltedHibernateUserAssembler.domainsToDtos(domain.getMembers()));
		dto.setAccountDto(accountAssembler.domainToDto(domain.getAccount()));

		return dto;
	}

	public Collection<GroupDto> domainsToDtos(final Collection<Group> domains){
		Collection<GroupDto> dtos = new ArrayList<GroupDto>();
		for (Group group : domains){
			dtos.add(domainToDto(group));
		}
		return dtos;
	}

	@Override
	public Collection<Group> dtosToDomains(final Collection<GroupDto> dtos) {
		Collection<Group> domains = new ArrayList<Group>();
		for (GroupDto page : dtos){
			domains.add(dtoToDomain(page));
		}
		return domains;
	}

}
