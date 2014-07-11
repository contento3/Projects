package com.c3.email.marketing.subscription.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SUBSCRIBER", schema ="MODULE_EMAIL_MARKETING")
public class Subscriber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name = "SUBSCRIBER_ID")
	private Integer subscriberId;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "SITE_USER_ID")
	private Integer siteUserId;
	
	@Column(name = "SITE_ID")
	private Integer siteId;

	public Integer getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(final Integer subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Integer getSiteUserId() {
		return siteUserId;
	}

	public void setSiteUserId(final Integer siteUserId) {
		this.siteUserId = siteUserId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(final Integer siteId) {
		this.siteId = siteId;
	}
}
