package com.contento3.account.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ACCOUNT", schema ="CMS")
public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "account_name")
	private String name;
	
	@Type(type = "true_false")
	@Column(name="IS_ENABLED")
	private boolean enabled;

	@OneToOne
	@JoinColumn(name = "ACCOUNT_TYPE_ID")
	private AccountType accountType;
	
//	private String description;
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	/*public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}*/
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(final AccountType accountType) {
		this.accountType = accountType;
	}
	
	
}
