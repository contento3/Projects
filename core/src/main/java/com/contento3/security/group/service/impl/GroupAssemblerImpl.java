package com.contento3.security.group.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.service.AccountAssembler;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;
import com.contento3.security.group.service.GroupAssembler;
import com.contento3.security.role.service.RoleAssembler;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;


public class GroupAssemblerImpl implements GroupAssembler {

	/**
	 * SaltedHibernateUserAssembler
	 */
	private SaltedHibernateUserAssembler saltedHibernateUserAssembler ;
	
	/**
	 * RoleAssembler
	 */
	private RoleAssembler roleAssembler;
	
	/**
	 * AccountAssembler
	 */
	private AccountAssembler accountAssembler;
	
	public GroupAssemblerImpl(final SaltedHibernateUserAssembler saltedHibernateUserAssembler,final AccountAssembler accountAssembler,final RoleAssembler roleAssembler) {
		this.saltedHibernateUserAssembler = saltedHibernateUserAssembler;
		this.accountAssembler = accountAssembler;
		this.roleAssembler = roleAssembler;
	}
	
	public Group dtoToDomain(final GroupDto dto,final Group domain){
		
		if (null!=dto.getGroupId()){
			domain.setGroupId(dto.getGroupId());
		}
		
		domain.setGroupName(dto.getGroupName());
		domain.setDescription(dto.getDescription());
		domain.setRoles(roleAssembler.dtosToDomains(dto.getRoles()));
		domain.setMembers(saltedHibernateUserAssembler.dtosToDomains(dto.getMembers()));
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccountDto()));
		return domain;
	}

	public GroupDto domainToDto(final Group domain,final GroupDto dto){
		
		if (null!=domain.getGroupId()){
			dto.setGroupId(domain.getGroupId());			
		}

		dto.setGroupName(domain.getGroupName());
		dto.setDescription(domain.getDescription());
		dto.setRoles(roleAssembler.domainsToDtos(domain.getRoles()));
		dto.setMembers(saltedHibernateUserAssembler.domainsToDtos(domain.getMembers()));
		dto.setAccountDto(accountAssembler.domainToDto(domain.getAccount()));
		return dto;
	}

	public Collection<GroupDto> domainsToDtos(final Collection<Group> domains){
		Collection<GroupDto> dtos = new ArrayList<GroupDto>();
		for (Group group : domains){
			dtos.add(domainToDto(group,new GroupDto()));
		}
		return dtos;
	}

	@Override
	public Collection<Group> dtosToDomains(final Collection<GroupDto> dtos) {
		Collection<Group> domains = new ArrayList<Group>();
		for (GroupDto dto : dtos){
			domains.add(dtoToDomain(dto,new Group()));
		}
		return domains;
	}

}
