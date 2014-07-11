package com.c3.email.marketing.subscription.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.account.model.Account;
import com.contento3.security.user.model.SaltedHibernateUser;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SUBSCRIPTION", schema ="MODULE_EMAIL_MARKETING")
public class Subscription {

	@Id @GeneratedValue
	@Column(name = "SUBSCRIPTION_ID")
	private Integer subscriptionId;
	
	@Column(name = "SUBSCRIPTION_NAME")
	private String subscriptionName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@OneToOne
	@JoinColumn(name = "USER_ID")
	private SaltedHibernateUser platformUser;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="SUBSCRIPTION_ID",nullable=false)
	private Collection <Subscriber> subscribers;

	public Collection<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(final Collection<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(final Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(final String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public SaltedHibernateUser getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(final SaltedHibernateUser platformUser) {
		this.platformUser = platformUser;
	}
	
}
	