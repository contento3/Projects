package com.contento3.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an operation for an entity.
 * @author hammad.afridi
 *
 */
@Entity
@Table(name = "ENTITY_OPERATION")
public class SecuredEntityOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENTITY_OPERATION_ID", length = 11, nullable = false, updatable = false)
	private Integer operationId;
	
	@Column(name = "ENTITY_OPERATION_NAME")
	private String name;

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(final Integer operationId) {
		this.operationId = operationId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
