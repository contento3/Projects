package com.contento3.security.group.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class GroupAuthorityLinkPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * AUTHORITY used in composite pk generation
	 */
	@Column(name="AUTHORITY")
	private String authority;
	
	/**
	 * GROUP_ID used in composite pk generation
	 */
	@ManyToOne
	@JoinColumn(name="GROUP_ID")
	private Group group;


	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

}
