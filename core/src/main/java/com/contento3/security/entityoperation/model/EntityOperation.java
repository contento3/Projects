package com.contento3.security.entityoperation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="ENTITY_OPERATION" , schema ="PLATFORM_USERS" )

public class EntityOperation {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ENTITY_OPERATION_ID")
	private Integer entityOperationId;

	@Column(name="ENTITY_OPERATION_NAME")
	private String entityOperationName;
	
	public void setEntityOperationName(final String entityoperationname)
	{
		this.entityOperationName=entityoperationname;
	}
	
	/**
	 * Returns the group name
	 * @param void
	 * @return
	 */
	public String getEntityOperationName()
	{
		return(entityOperationName);
	}
	
	/**
	 * Sets the group Id
	 * @param Group Id
	 * @return
	 */
	public void setEntityOperationId(final Integer entityOperationId)
	{
		this.entityOperationId=entityOperationId;
	}
	
	/**
	 * Returns the group Id // Chanfe these comments for role
	 * @param void
	 * @return
	 */
	public Integer getEntityOperationId()
	{
		return(entityOperationId);
	}


}
