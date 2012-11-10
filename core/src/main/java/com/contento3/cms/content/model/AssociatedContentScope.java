package com.contento3.cms.content.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="ASSOCIATED_CONTENT_SCOPE" )
public class AssociatedContentScope {

	/**
	 * Primary key
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	@Column( name = "ID")
	private Integer id;
	
	/**
	 * Scope of Associated Content
	 */
	@Column( name = "SCOPE")
	private String scope;

	/**
	 * Type of associated content
	 */
	@Column( name = "TYPE")
	private String type;

	public final Integer getId() {
		return id;
	}

	public final void setId(final Integer id) {
		this.id = id;
	}

	public final String getScope() {
		return scope;
	}

	public final void setScope(final String scope) {
		this.scope = scope;
	}
	
	public final String getType() {
		return type;
	}

	public final void setType(final String type) {
		this.type = type;
	}
}
