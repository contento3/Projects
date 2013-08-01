package com.contento3.security.entity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="ENTITY" , schema ="PLATFORM_USERS" )
public class PermissionEntity {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ENTITY_ID")
	private Integer entityId;

	@Column(name="ENTITY_NAME")
	private String entityName;
	
	public void setEntityName(final String entityname)
	{
		this.entityName=entityname;
	}
	
	/**
	 * Returns the group name
	 * @param void
	 * @return
	 */
	public String getEntityName()
	{
		return(entityName);
	}
	
	/**
	 * Sets the group Id
	 * @param Group Id
	 * @return
	 */
	public void setEntityId(final Integer entityId)
	{
		this.entityId=entityId;
	}
	
	/**
	 * Returns the group Id // Chanfe these comments for role
	 * @param void
	 * @return
	 */
	public Integer getEntityId()
	{
		return(entityId);
	}

}
