package com.contento3.security.group.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.contento3.account.model.Account;
import com.contento3.security.user.model.SaltedHibernateUser;

/**
 * 
 * Enable users to add groups
 * 
 */
@Entity
@Table( name="GROUP" , schema ="PLATFORM_USERS" )
public class Group {
	/**
	 * Primary key id for group
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="GROUP_ID")
	private Integer id;
	
	/**
	 * Group name
	 */
	@Column(name="GROUP_NAME")
	private String name;

	/**
	 * Group description
	 */
	@Column(name="DESCRIPTION")
	private String description;
	
	
	/**
	 * Authorities associated to group
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy="primaryKey.group")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE,
		org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Collection<GroupAuthority> authorities;
	
	/**
	 * Members associated to group
	 */
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name= "GROUP_MEMBER",
	joinColumns={
			@JoinColumn(name="GROUP_ID",unique= true)},
	inverseJoinColumns={
			@JoinColumn(name="USER_ID",unique= true)})
	private Collection<SaltedHibernateUser> members;
	

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	

	public Account getAccount() {
		return account;
	}


	public void setAccount(final Account account) {
		this.account = account;
	}


	public Collection<SaltedHibernateUser> getMembers() {
		return members;
	}

	public void setMembers(Collection<SaltedHibernateUser> members) {
		this.members = members;
	}

	/**
	 * Return Group authorities
	 * @return
	 */
	public Collection<GroupAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * set group authorities
	 * @param authorities
	 */
	public void setAuthorities(final Collection<GroupAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Sets the group name
	 * @param Group name
	 * @return
	 */
	public void setGroupName(final String groupName)
	{
		name=groupName;
	}
	
	/**
	 * Returns the group name
	 * @param void
	 * @return
	 */
	public String getGroupName()
	{
		return(name);
	}
	
	/**
	 * Sets the group Id
	 * @param Group Id
	 * @return
	 */
	public void setGroupId(final Integer groupId)
	{
		id=groupId;
	}
	
	/**
	 * Returns the group Id
	 * @param void
	 * @return
	 */
	public Integer getGroupId()
	{
		return(id);
	}
	
	/**
	 * Return group description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * sets the groups description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
