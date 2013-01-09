package com.contento3.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SecuredEntity that represents an 
 * entity in the system. 
 * @author hammad.afridi
 *
 */
@Entity
@Table(name = "Entity")
public class SecuredEntity {

	/**
	 * Entity id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENTITY_ID", length = 11, nullable = false, updatable = false)
	private Integer entityId;
	
	/**
	 * Entity name
	 */
	@Column(name = "ENTITY_NAME")
	private String entityName;

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(final Integer entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(final String entityName) {
		this.entityName = entityName;
	}
}
