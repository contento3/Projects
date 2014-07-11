package com.c3.email.marketing.subscription.dto;


public class SubscriberDto {

	private Integer subscriberId;

	private String email;

	private String firstName;

	private String lastName;

	private Integer siteUserId;
	
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
