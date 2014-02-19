package com.contento3.security.group.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;
import com.contento3.security.group.service.GroupAssembler;
import com.contento3.security.group.service.GroupService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;


@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class GroupServiceImpl implements GroupService {

	/**
	 * 
	 */
	public Collection<GroupDto> type;
	
	/**
	 * 
	 */
	private GroupAssembler groupAssembler;
	
	/**
	 * 
	 */
	private GroupDao groupDao;
	
	public GroupServiceImpl(final GroupDao groupDao,final GroupAssembler groupAssembler){
		Validate.notNull(groupDao,"groupDao cannot be null");
		Validate.notNull(groupAssembler,"groupAssembler cannot be null");
		
		/* Data access class to access the data objects for groups */
		this.groupDao = groupDao;
		
		/* assembler to convert GroupDto to Group and vice versa */
		this.groupAssembler = groupAssembler;
	}

	@RequiresPermissions("GROUP:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public GroupDto findByGroupName(String groupName,Integer accountId){
		Validate.notNull(groupName,"groupName cannot be null");
		return groupAssembler.domainToDto(groupDao.findByGroupName(groupName,accountId),new GroupDto());
	}

	@RequiresPermissions("GROUP:VIEW_LISTING")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<GroupDto> findByAccountId(final Integer accountId){
		Validate.notNull(accountId,"groupName cannot be null");
		return groupAssembler.domainsToDtos(groupDao.findByAccountId(accountId));
	}

	@RequiresPermissions("GROUP:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(final GroupDto groupDto) throws EntityAlreadyFoundException {
		Validate.notNull(groupDto,"groupDto cannot be null");
		Group group = groupDao.findByGroupName(groupDto.getGroupName(), groupDto.getAccountDto().getAccountId());
		if (null!=group){
			throw new EntityAlreadyFoundException("Group with same name already exist");
		}
		group = new Group();
		return groupDao.persist(groupAssembler.dtoToDomain(groupDto,new Group()));
	}

	@RequiresPermissions("GROUP:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public GroupDto findById(Integer id) {
		Validate.notNull(id,"id cannot be null");
		return groupAssembler.domainToDto(groupDao.findById(id),new GroupDto());
	}
	
	@RequiresPermissions("GROUP:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final GroupDto groupDto) throws EntityAlreadyFoundException {
		Validate.notNull(groupDto,"groupDto cannot be null");
		Group group = groupDao.findByGroupName(groupDto.getGroupName(), groupDto.getAccountDto().getAccountId());
		if (null!=group && !group.getGroupId().equals(groupDto.getGroupId())){
			throw new EntityAlreadyFoundException("Group with same name already exist");
		}
		else if (null==group) {
			group = new Group();
		}
		
		groupDao.update(groupAssembler.dtoToDomain(groupDto,group));
	}
	
	@RequiresPermissions("GROUP:DELETE")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(GroupDto group) {
		Validate.notNull(group,"group cannot be null");
		Collection<SaltedHibernateUserDto> members;
		members=group.getMembers();
		
		if(members.isEmpty())
			groupDao.delete(groupAssembler.dtoToDomain(group,new Group()));
	}

	@RequiresPermissions("GROUP:DELETE")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteWithException(GroupDto group) throws EntityCannotBeDeletedException {
		Validate.notNull(group,"group cannot be null");
		Collection<SaltedHibernateUserDto> members;
		members=group.getMembers();
		
		if(members.isEmpty())
			groupDao.delete(groupAssembler.dtoToDomain(group,new Group()));
		else
			throw new EntityCannotBeDeletedException("You can`t delete group because users are associated to group");	
		
		if(group.getRoles().isEmpty())
			groupDao.delete(groupAssembler.dtoToDomain(group,new Group()));
		else
			throw new EntityCannotBeDeletedException("You can`t delete group because roles are associated to group");
	}
	
	@RequiresPermissions("GROUP:VIEW_LISTING")
	@Override
	public Collection<GroupDto> findByUserId(Integer id) {
		return groupAssembler.domainsToDtos(groupDao.findByUserId(id));
	}

	
	
	
}


