package com.contento3.security.group.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;
import com.contento3.security.group.service.GroupAssembler;


public class GroupAssemblerImpl implements GroupAssembler {

	public Group dtoToDomain(final GroupDto dto){
		Group group = new Group();
		group.setGroupId(dto.getGroupId());
		group.setGroupName(dto.getGroupName());
		group.setDescription(dto.getDescription());
		return group;
	}

	public Group dtoToDomain(final GroupDto dto,final Group domain){
		domain.setGroupId(dto.getGroupId());
		domain.setGroupName(dto.getGroupName());
		domain.setDescription(dto.getDescription());
		return domain;
	}

	public GroupDto domainToDto(final Group domain){
		GroupDto dto = new GroupDto();
		dto.setGroupId(domain.getGroupId());
		dto.setGroupName(domain.getGroupName());
		dto.setDescription(domain.getDescription());
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
	public Collection<Group> dtosToDomains(Collection<GroupDto> dtos) {
		Collection<Group> domains = new ArrayList<Group>();
		for (GroupDto page : dtos){
			domains.add(dtoToDomain(page));
		}
		return domains;
	}

}
