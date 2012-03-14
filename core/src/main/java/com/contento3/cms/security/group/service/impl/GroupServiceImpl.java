package com.contento3.cms.security.group.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.security.group.dao.GroupDao;
import com.contento3.cms.security.group.dto.GroupDto;
import com.contento3.cms.security.group.model.Group;
import com.contento3.cms.security.group.service.GroupAssembler;
import com.contento3.cms.security.group.service.GroupService;
import com.contento3.cms.security.group.service.impl.GroupServiceImpl;


@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class GroupServiceImpl implements GroupService {

	private GroupAssembler groupAssembler;
	private GroupDao groupDao;
	public GroupServiceImpl(final GroupDao groupDao,final GroupAssembler groupAssembler){
		this.groupDao = groupDao;
		this.groupAssembler = groupAssembler;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Collection<GroupDto> findByGroupName(String groupName){
		return groupAssembler.domainsToDtos(groupDao.findByGroupName(groupName));
	}
	private Collection<GroupDto> type;
	public Collection<GroupDto> findAllGroups(){
		return groupAssembler.domainsToDtos(groupDao.findAll());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(final GroupDto groupDto) {
		groupDao.persist(groupAssembler.dtoToDomain(groupDto));
	}
	
}


