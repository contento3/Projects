package com.contento3.security.role.model;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.contento3.security.permission.model.Permission;
import org.hibernate.annotations.Cascade;

import com.contento3.account.model.Account;
@Entity
@Table( name="ROLE" , schema ="PLATFORM_USERS" )

public class Role {
	@Id
	@Column(name="ROLE_ID")
	private Integer roleid;

	@Column(name="ROLE_NAME")
	private String roleName;

	@Column(name="DESCRIPTION")
	private String description;

	/**
	 * Permissions associated to role
	 */
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name= "ROLE_PERMISSION",
	joinColumns={
			@JoinColumn(name="ROLE_ID",unique= true)},
	inverseJoinColumns={
			@JoinColumn(name="PERMISSION_ID",unique= true)})
	private Collection<Permission> permissions;
	
	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}
	
	
	/**
	 * 
	 * @param rolename
	 */
	public void setRoleName(final String rolename)
	{
		this.roleName=rolename;
	}
	
	/**
	 * Returns the group name
	 * @param void
	 * @return
	 */
	public String getRoleName()
	{
		return(roleName);
	}
	
	/**
	 * Sets the group Id
	 * @param Group Id
	 * @return
	 */
	public void setRoleId(final Integer roleId)
	{
		this.roleid=roleId;
	}
	
	/**
	 * Returns the group Id // Chanfe these comments for role
	 * @param void
	 * @return
	 */
	public Integer getRoleId()
	{
		return(roleid);
	}
	/**
	 * sets the groups description
	 * @param description
	 */
	public void setDescription(final String Description) {
		this.description = Description;
	}
	
	/**
	 * Return group description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	public Account getAccount() {
		return account;
	}


	public void setAccount(final Account account) {
		this.account = account;
	}
	



	/**
	 * Return Role Permissions
	 * @return
	 */
/*	public Collection<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * set group authorities
	 * @param authorities
	 */
//	public void setPermissions(final Collection<Permission> permissions) {
	//	this.permissions = permissions;
	//}


}
