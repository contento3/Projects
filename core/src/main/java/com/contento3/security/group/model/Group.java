package com.contento3.security.group.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * Enable users to add groups
 * 
 */
@Entity
@Table(name= "groups")
public class Group {
	/**
	 * Primary key id for group
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	/**
	 * Group name
	 */
	@Column(name="group_name")
	private String name;

	/**
	 * Group description
	 */
	@Column(name="description")
	private String description;
	
	
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
	
	/**
	 * Return group description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * sets the groups description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
