package com.c3.email.marketing.newsletter.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.c3.email.marketing.subscription.model.Subscription;
import com.contento3.account.model.Account;
import com.contento3.cms.page.category.model.Category;
import com.contento3.cms.page.template.model.Template;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "NEWSLETTER", schema ="MODULE_EMAIL_MARKETING")
public class Newsletter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name = "NEWSLETTER_ID")
	private Integer newsletterId;
	
	@Column(name = "NEWSLETTER_NAME")
	private String newsletterName;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToOne
	@JoinColumn(name = "template_id")
	private Template template;
	
	@JoinColumn(name = "subject")
	private String subject;

	@ManyToMany
	@JoinTable(name="NEWSLETTER_SUBSCRIPTION", schema="module_email_marketing",
		joinColumns={@JoinColumn(name="NEWSLETTER_ID",unique=true)},
		inverseJoinColumns={@JoinColumn(name="SUBSCRIPTION_ID")})
	private Collection <Subscription> subscriptionList;

	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public Integer getNewsletterId() {
		return newsletterId;
	}

	public void setNewsletterId(final Integer newsletterId) {
		this.newsletterId = newsletterId;
	}

	public String getNewsletterName() {
		return newsletterName;
	}

	public void setNewsletterName(final String newsletterName) {
		this.newsletterName = newsletterName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(final Template template) {
		this.template = template;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public Collection <Subscription> getSubscriptionList() {
		return subscriptionList;
	}

	public void setSubscriptionList(final Collection <Subscription> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}
}
