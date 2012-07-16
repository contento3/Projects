package com.contento3.security.group.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;
import com.contento3.security.group.service.GroupAssembler;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.group.service.impl.GroupServiceImpl;


@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class GroupServiceImpl implements GroupService {

	private GroupAssembler groupAssembler;
	private GroupDao groupDao;
	
	public GroupServiceImpl(final GroupDao groupDao,final GroupAssembler groupAssembler){
		
		/* Data access class to access the data objects for groups */
		this.groupDao = groupDao;
		
		/* assembler to convert GroupDto to Group and vice versa */
		this.groupAssembler = groupAssembler;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public GroupDto findByGroupName(String groupName){
		return groupAssembler.domainToDto(groupDao.findByGroupName(groupName));
	}
	
	public Collection<GroupDto> type;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<GroupDto> findAllGroups(){
		return groupAssembler.domainsToDtos(groupDao.findAll());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(final GroupDto groupDto) {
		groupDao.persist(groupAssembler.dtoToDomain(groupDto));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public GroupDto findById(Integer id) {
		return groupAssembler.domainToDto(groupDao.findById(id));
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(GroupDto groupDto) {
		groupDao.update(groupAssembler.dtoToDomain(groupDto));
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(GroupDto group) {
		groupDao.delete(groupAssembler.dtoToDomain(group));
		
	}
	
	
}


