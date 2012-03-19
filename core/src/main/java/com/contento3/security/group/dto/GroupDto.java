package com.contento3.security.group.dto;

public class GroupDto {
	
	private Integer id;
	
	private String name;
	
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
