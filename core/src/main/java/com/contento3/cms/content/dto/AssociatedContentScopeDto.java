package com.contento3.cms.content.dto;

import com.contento3.common.dto.Dto;

public class AssociatedContentScopeDto extends Dto{
	
	/**
	 * Primary key
	 */
	private Integer id;
	
	/**
	 * Scope of associated Content
	 */
	private String scope;

	/**
	 * Type of associated content
	 */
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

	public String getName() {
		return this.scope;
	}


}
