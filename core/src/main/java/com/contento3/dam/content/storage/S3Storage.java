package com.contento3.dam.content.storage;

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

import com.contento3.account.model.Account;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "AWSS3", schema = "STORAGE_INFRASTRUCTURE")
public class S3Storage implements Storage {
	
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column( name = "ID")
	private Integer id;
	
	@Column( name = "END_POINT")
	private String endPoint;
	
	@Column( name = "BUCKET_NAME")
	private String bucket;
	
	@Column( name = "SECRET_KEY")
	private String secretKey;
	
	@Column( name = "ACCESS_KEY")
	private String accessKey;
	
	@OneToOne
	@JoinColumn( name = "ACCOUNT_ID")
	private Account account;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(final String endPoint) {
		this.endPoint = endPoint;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(final String bucket) {
		this.bucket = bucket;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(final String secretKey) {
		this.secretKey = secretKey;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(final String accessKey) {
		this.accessKey = accessKey;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}
	

}
