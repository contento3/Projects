package com.contento3.security.group.dto;

import java.util.Collection;

import com.contento3.common.dto.Dto;

import com.contento3.security.group.model.GroupAuthority;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public class GroupDto extends Dto {
	/**
	 * Primary key id for group
	 */
	private Integer id;
	
	/**
	 * Group name
	 */
	private String name;
	
	/**
	 * Group description
	 */
	private String description;
	
	/**
	 * Authorities associated to group
	 */
	private Collection<GroupAuthority> authorities;
	
	/**
	 * Members associated to group
	 */
	private Collection<SaltedHibernateUserDto> members;
	
	/**
	 * Return group related authorites
	 * @return
	 */
	public Collection<GroupAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Sets group authorities
	 * @param authorities
	 */
	public void setAuthorities(final Collection<GroupAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Return group related users
	 * @return
	 */
	public Collection<SaltedHibernateUserDto> getMembers() {
		return members;
	}

	/**
	 * Sets group members (users)
	 * @param authorities
	 */
	public void setMembers(final Collection<SaltedHibernateUserDto> members) {
		this.members = members;
	}

	/**
	 * Return group description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set group description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the group name
	 * @param Group name
	 * @return
	 */
	public void setGroupName(final String groupName)
	{
		name=groupName;
	}
	
	/**
	 * Returns the group name
	 * @param void
	 * @return
	 */
	public String getGroupName()
	{
		return(name);
	}
	
	/**
	 * Sets the group Id
	 * @param Group Id
	 * @return
	 */
	public void setGroupId(final Integer groupId)
	{
		id=groupId;
	}
	
	/**
	 * Returns the group Id
	 * @param void
	 * @return
	 */
	public Integer getGroupId()
	{
		return(id);
	}

}
