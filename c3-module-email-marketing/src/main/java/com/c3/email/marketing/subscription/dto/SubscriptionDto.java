package com.c3.email.marketing.subscription.dto;

import java.util.Collection;
import java.util.Date;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public class SubscriptionDto extends Dto {

	private Integer subscriptionId;
	
	private String subscriptionName;
	
	private String description;
	
	private AccountDto account;

	private Date createdOn;
	
	private SaltedHibernateUserDto platformUser;

	private Collection <SubscriberDto> subscribers;
	
	@Override
	public Integer getId(){
		return this.subscriptionId;
	}
	
	@Override
	public String getName(){
		return this.subscriptionName;
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

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(final AccountDto account) {
		this.account = account;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public SaltedHibernateUserDto getPlatformUser() {
		return platformUser;
	}

	public void setPlatformUser(final SaltedHibernateUserDto platformUser) {
		this.platformUser = platformUser;
	}

	public Collection<SubscriberDto> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(final Collection<SubscriberDto> subscribers) {
		this.subscribers = subscribers;
	}

}
