package com.contento3.security.group.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="GROUP_AUTHORITIES")
public class GroupAuthority implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Composite primary key for GroupAuthority
	 */
	@EmbeddedId
	private GroupAuthorityLinkPK primaryKey;

	public GroupAuthorityLinkPK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(final GroupAuthorityLinkPK primaryKey) {
		this.primaryKey = primaryKey;
	}


	
}
