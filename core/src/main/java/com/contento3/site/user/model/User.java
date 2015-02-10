package com.contento3.site.user.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.cms.site.structure.model.Site;

/**
 * A model class that represents a registered website user
 * @author Hammad Afridi
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(schema="CRM", name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="USERNAME") 
	private String username;
	
	@Column(name="PASSWORD") 
	private String password;
	
	@Column(name="PASSWORD_REMINDER") 
	private String passwordReminder;

	@Column(columnDefinition="TEXT", length = 100, name = "UUID", unique=true, nullable=false) 
	private String uuid = UUID.randomUUID().toString();

	@OneToOne
	@JoinColumn(name = "SITE_ID")
	private Site site;

	@Column(name="IS_ENABLED")
	private boolean enabled;
	
	@Column(name="IS_EMAIL_USERNAME") 
	private boolean isEmailUsername;

	@Column(name="SALT")
	private String salt;

	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="MIDDLE_NAME")
	private String middleName;
	
	
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}

	public Integer getId() {
		return id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPasswordReminder() {
		return passwordReminder;
	}

	public void setPasswordReminder(final String passwordReminder) {
		this.passwordReminder = passwordReminder;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public boolean isEmailUsername() {
		return isEmailUsername;
	}

	public void setEmailUsername(final boolean isEmailUsername) {
		this.isEmailUsername = isEmailUsername;
	}

	public void setSite(final Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}
	
}
