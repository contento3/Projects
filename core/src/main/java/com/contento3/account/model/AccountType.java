package com.contento3.account.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ACCOUNT_TYPE", schema ="MODULES")
public class AccountType {

	@Id @GeneratedValue
	@Column(name = "ACCOUNT_TYPE_ID")
	private Integer accountTypeId;
	
	@Column(name = "ACCOUNT_TYPE_NAME")
	private String accountTypeName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * Modules for this account.
	 */
	@ManyToMany
	@JoinTable(name="ACCOUNT_TYPE_MODULE", schema="MODULES" ,
		joinColumns={@JoinColumn(name="ACCOUNT_TYPE_ID",unique=true)},
		inverseJoinColumns={@JoinColumn(name="MODULE_ID")})
	private Collection<Module> modules;

	public Integer getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Module> getModules() {
		return modules;
	}

	public void setModules(Collection<Module> modules) {
		this.modules = modules;
	}

}
