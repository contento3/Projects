package com.contento3.cms.security.group.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name= "groups")
public class Group {
	
	@Column(name="id")
	private String id;
	
	@Column(name="group_name")
	private String name;
	
	void setGroupName(String g_name)
	{
		name=g_name;
	}
	
	String getGroupName()
	{
		return(name);
	}
	
}
